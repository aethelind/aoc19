# https://adventofcode.com/2019/day/1
# my ans: 3454026

# find fuel required given mass of each module, with formula floor(mass / 3) - 2

puts File.open("input.txt").readlines.map(&:to_i).inject(0) { |sum, mass| sum + (mass/3) - 2}
