import sys
import os
import re

"""An example of beautified index.html. All content is on one line in real world.
<thead>
        <tr>
          <td class="sortable" id="a" onclick="toggleSort(this)">Element</td>
          <td class="down sortable bar" id="b" onclick="toggleSort(this)">Missed Instructions</td>
          <td class="sortable ctr2" id="c" onclick="toggleSort(this)">Cov.</td>
          <td class="sortable bar" id="d" onclick="toggleSort(this)">Missed Branches</td>
          <td class="sortable ctr2" id="e" onclick="toggleSort(this)">Cov.</td>
          <td class="sortable ctr1" id="f" onclick="toggleSort(this)">Missed</td>
          <td class="sortable ctr2" id="g" onclick="toggleSort(this)">Cxty</td>
          <td class="sortable ctr1" id="h" onclick="toggleSort(this)">Missed</td>
          <td class="sortable ctr2" id="i" onclick="toggleSort(this)">Lines</td>
          <td class="sortable ctr1" id="j" onclick="toggleSort(this)">Missed</td>
          <td class="sortable ctr2" id="k" onclick="toggleSort(this)">Methods</td>
          <td class="sortable ctr1" id="l" onclick="toggleSort(this)">Missed</td>
          <td class="sortable ctr2" id="m" onclick="toggleSort(this)">Classes</td>
        </tr>
      </thead>
      <tfoot>
        <tr>
          <td>Total</td>
          <td class="bar">1,323 of 26,881</td>
          <td class="ctr2">95%</td>
          <td class="bar">141 of 2,074</td>
          <td class="ctr2">93%</td>
          <td class="ctr1">209</td>
          <td class="ctr2">2,558</td>
          <td class="ctr1">328</td>
          <td class="ctr2">6,228</td>
          <td class="ctr1">77</td>
          <td class="ctr2">1,489</td>
          <td class="ctr1">15</td>
          <td class="ctr2">289</td>
        </tr>
      </tfoot>
"""

# index map used to find missed and total values for different counters
# different types of jacoco counters are explained here: https://www.jacoco.org/jacoco/trunk/doc/counters.html
INDEX_MAP = {
    4: "CXTY",
    6: "LINES",
    8: "METHODS",
    10: "CLASSES",
}


def get_stats(path):
    """

    :return: a dictionary, key is the coverage counter name, value is a tuple of (missed, total, coverage_pct)
    """
    stats = {}

    if not os.path.isfile(path):
        print("Given file path {} does not exist, exit program".format(path))
        return stats

    # ideally the report is already in one line, we combine it into one line just in case
    with open(path, 'r') as f:
        oneline = "".join(line.strip() for line in f)
    # print(oneline)

    try:
        tfoot_start = oneline.index("<tfoot>")
        tfoot_end = oneline.index("</tfoot>", tfoot_start)
        tfoot = oneline[tfoot_start:tfoot_end]
        # print "Found <tfoot> element: {}".format(tfoot)
    except ValueError:
        print("Cannot find <tfoot> element in jacoco report file, exit program")
        return stats

    pattern = '<td class="[^"]+">([0-9of,% ]+)</td>'
    results = re.findall(pattern, tfoot)
    # print(results)

    # special handling for instruction counter value, in the form of 'missed of total'
    instruction_missed_total = get_missed_total(results[0])
    stats["INSTRUCTION"] = (instruction_missed_total[0],
                            instruction_missed_total[1],
                            calc_pct(instruction_missed_total[0], instruction_missed_total[1]))

    # special handling of branch counter value, in the form of 'missed of total'
    branch_missed_total = get_missed_total(results[2])
    stats["BRANCH"] = (branch_missed_total[0],
                       branch_missed_total[1],
                       calc_pct(branch_missed_total[0], branch_missed_total[1]))

    # other counters have separate values for missed and total
    for index in range(4, 12, 2):
        stats[INDEX_MAP[index]] = (results[index], results[index + 1], calc_pct(results[index], results[index + 1]))

    # print(stats)
    return stats


def get_missed_total(missed_and_total):
    """Parses a string in the format of ``m of n`` where m is the missed, n is the total.

    :param missed_and_total: input string to parse
    :return: a tuple of two elements, the first element is the missed count, the second element is the total count
    """
    parts = missed_and_total.split(" of ")
    if len(parts) == 2:
        return parts[0], parts[1]
    else:
        return ()


def calc_pct(missed, total):
    """Calculates coverage percentage.

    :param missed: string value of missed count, e.g. 77
    :param total: string value of total count, e.g. 1,489
    :return: float value of percentage
    """
    return 100.0 - float(missed.replace(',', '')) / float(total.replace(',', '')) * 100.0


def diff_stats(path, old_path):
    """Calculate coverage stats difference between a current report and an old report.

    :param path: path to the current report index.html
    :param old_path: path to the old report index.html
    :return: a dictionary for coverage data differences, key is the counter name, value is a tuple with four values:
        current miss count, current total count, current coverage percentage, increase or drop from old coverage percentage
    """
    stats = get_stats(path)
    old_stats = get_stats(old_path)

    diff_stats = {}

    for counter, values in stats.items():
        diff_stats[counter] = (values[0], values[1], values[2], values[2] - old_stats[counter][2])

    return diff_stats


def print_stats(stats):
    """Pretty print the final coverage stats.

    :param stats: a dictionary, key is the coverage counter name, value is a tuple of (missed, total, coverage_pct)
    :return:
    """
    for counter, values in stats.items():
        print("counter: {} - missed: {}, total: {}, coverage: {}%".format(counter, values[0], values[1], values[2]))


def print_diff_stats(diff_stats):
    """Pretty print the coverage stats differences.

    :param diff_stats: a dictionary containing coverage diff
    """
    for counter, values in diff_stats.items():
        print("counter: {} - missed: {}, total: {}, coverage: {}%, change: {}%".format(counter, values[0], values[1],
                                                                                       values[2], values[3]))


if __name__ == "__main__":
    arg_count = len(sys.argv)
    if arg_count < 2:
        print("Missing the required path to jacoco report index.html")
        print("Usage: python jacoco_parser/parser.py path_to_index.html [path_to_old_index.html]")
        sys.exit(1)
    elif arg_count == 2:
        print_stats(get_stats(sys.argv[1]))
    else:
        print_diff_stats(diff_stats(sys.argv[1], sys.argv[2]))
