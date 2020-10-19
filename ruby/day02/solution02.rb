# part one
# https://adventofcode.com/2019/day/2
# my ans: 5305097 

# write intcode computer.
# code 1 looks at the position indicated in the next two positions, and places their sum in the position indicated b the third positon.
# code 2 does the same with their product.
# code 99 halts
# also changed input[1] with 12, and [2] with 2.

puts "Part One:"

def intcode(ops)
	ptr = 0

	loop do
		case ops[ptr]
		when 1
			ops[ops[ptr+3]] = ops[ops[ptr+1]] + ops[ops[ptr+2]]
		when 2
			ops[ops[ptr+3]] = ops[ops[ptr+1]] * ops[ops[ptr+2]]
		when 99
			return ops[0]
			break
		else
			# raise "invalid opcode sequence"
			return -1
			break
		end
		ptr+=4
	end
end

ops = File.open("input.txt").read.split(",").map(&:to_i)
ops[1] = 12
ops[2] = 2
puts intcode(ops)

# part two
# https://adventofcode.com/2019/day/2#part2
# my ans: 4925

# find the values for ops[1] (noun) and ops[2] (verb) s/t result is desired_output
# values are in range (0, 99)
puts "\nPart Two:"

def findNounVerb()
	desired_output = 19690720
	unaltered = File.open("input.txt").read.split(",").map(&:to_i)
	for noun in 0..99 do
		for verb in 0..99 do
			ops = unaltered.clone
			ops[1] = noun
			ops[2] = verb
			result = intcode(ops)
			if result == desired_output
				puts "Noun: " + noun.to_s
				puts "Verb: " + verb.to_s
				return 100 * noun + verb
			end
		end
	end
end

puts findNounVerb()
