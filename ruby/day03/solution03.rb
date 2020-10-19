# part one
# https://adventofcode.com/2019/day/3
# my ans: 2050

# get manhattan distance of closest wire intersection

require 'set'

# manhattan dist from origin
def manhattan(pt)
	return pt[0].abs + pt[1].abs
end

def walk(path)
    panel = Set.new()
    
    x = 0
    y = 0

    path.split(",").each do |cmd|
        cmd.delete("^0-9").to_i.times {
            case cmd[0]
            when 'U'
                y += 1
            when 'D'
                y -= 1
            when 'L'
                x -= 1
            when 'R'
                x += 1
            else
                raise "invalid direction"
            end

            panel << [x, y]
        }
    end
    return panel
end


wires = File.open("input.txt").readlines
puts "Part One:"
puts (walk(wires[0]) & walk(wires[1])).inject([]) { |mans, pt| mans << manhattan(pt)}.min
# get the set of pts each wire passes through, get the intersections, get manhattan distances and find minimum. 

# part two
# https://adventofcode.com/2019/day/3#part2
# my ans: _

# find the intersection that takes the fewest number of combined steps

# must keep track of #steps and associate the points with them


def step(path)
    panel = Set.new()
    
    x = 0
    y = 0
    steps = 0

    path.split(",").each do |cmd|
        cmd.delete("^0-9").to_i.times {
            case cmd[0]
            when 'U'
                y += 1
            when 'D'
                y -= 1
            when 'L'
                x -= 1
            when 'R'
                x += 1
            else
                raise "invalid direction"
            end

            panel << [[x, y], steps += 1]
        }
    end
    return panel
end

wires = File.open("input.txt").readlines
puts "Part Two:"

# path_a = step(wires[0])
# path_b = step(wires[1])

# mutual = []
# path_a.select { |wire| path_b.include(wire)? }