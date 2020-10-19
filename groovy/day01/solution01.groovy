// part one
// https://adventofcode.com/2019/day/1
// my ans: 3454026

// get input
def data = new File('groovy/day01/input.txt').text.split("\n")

// find fuel required given mass of each module, with formula floor(mass / 3) - 2
println "Part One:"
println data.sum { (it as Integer).intdiv(3) - 2 }

// puts File.open("input.txt").readlines.map(&:to_i).inject(0) { |sum, mass| sum + (mass/3) - 2}

// part two
// https://adventofcode.com/2019/day/1#part2
// my ans: 5178170

// include the mass of the fuel itself in the calculation
println "Part Two:"

def getFuel(mass) {
	def fuel = 0

	while(mass > 8){ // while non-negative fuel required
		mass = mass.intdiv(3) - 2 // calculate fuel required && new mass is this fuel addition
		fuel += mass // add it to total fuel req
	}
	return fuel
}

println data.sum { getFuel(it as Integer) }
