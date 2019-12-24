# part one
# https://adventofcode.com/2019/day/4
# my ans: 579

# find number of possible passwords within given range
# w/ given rules:
    # It is a six-digit number.
    # The value is within the range given in your puzzle input.
    # Two adjacent digits are the same (like 22 in 122345).
    # Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

def passFinder(l, u)
    passwords = 0

    for pass in l..u
        adjacent = false
        increasing = true
        prev = -1

        for i in 0..5
            curr = pass.to_s[i].to_i
            
            if prev == curr
                adjacent = true
            elsif prev > curr
                increasing = false
            end

            prev = curr
        end
        
        if adjacent && increasing
            passwords += 1
        end
    end

    return passwords
end

range = File.open("input.txt").read.split("-").map(&:to_i)
puts passFinder(range[0], range[1])

# part two
# https://adventofcode.com/2019/day/4#part2
# my ans: _

# explanation