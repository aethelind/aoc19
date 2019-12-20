# part one
# https://adventofcode.com/2019/day/1
# my ans: 3454026

# find fuel required given mass of each module, with formula floor(mass / 3) - 2
puts "Part One:"
puts File.open("input.txt").readlines.map(&:to_i).inject(0) { |sum, mass| sum + (mass/3) - 2}

# part two
# https://adventofcode.com/2019/day/1#part2
# my ans: 5178170

# include the mass of the fuel itself in the calculation
puts "\nPart Two:"

def getFuel(mass)
	sum = 0
	while mass > 8
		sum += mass = (mass/3) - 2
	end
	return sum
end

puts File.open("input.txt").readlines.map(&:to_i).inject(0) { |sum, mass| sum + getFuel(mass)}
