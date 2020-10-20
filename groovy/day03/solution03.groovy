// part one
// https://adventofcode.com/2019/day/3
// my ans: 2050

// get manhattan distance of closest wire intersection
// get input

def paths = new File('groovy/day03/input.txt').text.split("\n").collect { it.split(",") }

// method that walks along each pt in path eg R75,D30,R83,U83,L12,D49,R71,U7,L72

def walk (path) {
  def panel = [] as Set

  def x = 0
  def y = 0

  for (step in path) {
    def direction = step[0] // first letter = U/D/L/R
    def steps = 1..(step[1..-1] as int) // a range from 1 to the number of steps taken in that direction

    switch (direction) {
      case "U":
        steps.each { panel << [x, ++y] }
        break
      case "D":
        steps.each { panel << [x, --y] }
        break
      case "L":
        steps.each { panel << [--x, y] }
        break
      case "R":
        steps.each { panel << [++x, y] }
        break
      default:
        throw new Exception("Direction '${direction}' not recognized")
    }
  }
  return panel
}

// manhattan distance to [0, 0] is absolute values of coords added up
def manhattan(pt) {
  return Math.abs(pt[1]) + Math.abs(pt[0])
}

testPaths = [["R8","U5","L5","D3"], ["U7","R6","D4","L4"]]


// walk(path) gets all the pts along a path.
// intersect gets the pts that are on both paths
// min gets pt with lowest manhattan distance

print "Part One: "
println manhattan(walk(paths[0]).intersect(walk(paths[1])).min { manhattan(it) })

// part two
// https://adventofcode.com/2019/day/3#part2
// my ans: 21666

// find the intersection that takes the fewest number of combined steps

// must keep track of #steps and associate the points with them

def stepper (path) {
  def panel = [:] as Map

  def x = 0
  def y = 0

  def stepCount = 0

  for (step in path) {
    def direction = step[0] // first letter = U/D/L/R
    def steps = 1..(step[1..-1] as int) // a range from 1 to the number of steps taken in that direction

    switch (direction) {
      case "U":
        // if a point has already been visited, then don't mark it's step count again.
        steps.each { panel.containsKey([x, ++y]) ? stepCount++ : panel.put([x, y], ++stepCount) }
        break
      case "D":
        steps.each { panel.containsKey([x, --y]) ? stepCount++ : panel.put([x, y], ++stepCount) }
        break
      case "L":
        steps.each { panel.containsKey([--x, y]) ? stepCount++ : panel.put([x, y], ++stepCount) }
        break
      case "R":
        steps.each { panel.containsKey([++x, y]) ? stepCount++ : panel.put([x, y], ++stepCount) }
        break
      default:
        throw new Exception("Direction '${direction}' not recognized")
    }
  }
  return panel
}

def totalSteps (k, m1, m2) {
  return m1[k] + m2[k]
}

def m1 = stepper(paths[0])
def m2 = stepper(paths[1])

print "Part Two: "
println totalSteps((m1.keySet().intersect(m2.keySet()).min { totalSteps(it, m1, m2) }), m1, m2)

// find intersecting points, then add their respective stepcounts, and take the lowest one.
