# part one
# https://adventofcode.com/2019/day/2
# my ans: 5305097

# write intcode computer.
# cod 1 looks at the position indicated in the next two positions, and places their sum in the position indicated b the third positon.
# code 2 does the same with their product.
# code 99 halts
# also changed input[1] with 12, and [2] with 2.

puts "Part One:"

def intcode(op)
	curr = 0

	while true
		case op[curr]
		when 1
			op[op[curr+3]] = op[op[curr+1]] + op[op[curr+2]]
		when 2
			op[op[curr+3]] = op[op[curr+1]] * op[op[curr+2]]
		when 99
			puts op[0]
			break
		else
			raise "invalid opcode sequence"
			break
		end
		curr+=4
	end
end

intcode(File.open("input.txt").read.split(",").map(&:to_i))

# part two
# https://adventofcode.com/2019/day/2#part2
# my ans: _

# ...
puts "\nPart Two:"

