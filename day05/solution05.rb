# part one
# https://adventofcode.com/2019/day/5
# my ans: _

# write intcode computer.
# code 1 looks at the position indicated in the next two positions, and places their sum in the position indicated b the third positon.
# code 2 does the same with their product.
# code 99 halts
# new:
# code 3 takes an input and saves it in the location given by its parameter. eg. 3,50
# code 4 gives an output, its only parameter
# these inputs and outputs are given.
# add support for immediate mode 1, where a parameter is interpreted as a value ratjer than a place.


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
# https://adventofcode.com/2019/day/5#part2
# my ans: _

# 


range = File.open("input.txt").read.split("-").map(&:to_i)
puts passFinderDouble(range[0], range[1])