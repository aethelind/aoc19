// part one
// https://adventofcode.com/2019/day/4
// my ans: 579

// find number of possible passwords within given range
// w/ given rules:
//     It is a six-digit number.
//     The value is within the range given in your puzzle input.
//     Two adjacent digits are the same (like 22 in 122345).
//     Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

// get input
def range = new File('groovy/day04/input.txt').text.split("-").collect { it as int }

def countValidPasswords(range) {
  def validPasswords = 0

  (range[0]..range[1]).each { r ->
    def adjacent = false
    def increasing = true
    def prev = -1
    (r as String).each { i ->
      def curr = i as int
      if (curr == prev) {
        adjacent = true
      } else if (prev > curr) {
        increasing = false
      }
      prev = curr
    }
     if (adjacent && increasing) { validPasswords++ }
  }

  return validPasswords
}

print "Part One: "
println countValidPasswords(range)

// part two
// https://adventofcode.com/2019/day/4#part2
// my ans: 358

// new rule; there must be at least one instance of only two repeating digits

def countValidPasswords2(range) {
  def validPasswords = 0

  (range[0]..range[1]).each { r ->
    def dubs = false
    def increasing = true
    def prev = -1
    def adjacentCount = 1

    (r as String).each { i ->
      def curr = i as int

      if (curr == prev) {
        adjacentCount++
      } else {
        if (adjacentCount == 2) { dubs = true }
        adjacentCount = 1
      }

      if (prev > curr) {
        increasing = false
      }

      prev = curr
    }
     if ((dubs || (adjacentCount == 2)) && increasing) { validPasswords++ }
  }

  return validPasswords
}

print "Part Two: "
println countValidPasswords2(range)
