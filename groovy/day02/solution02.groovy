// part one
// https://adventofcode.com/2019/day/2
// my ans: 5305097

// common utilities
def setData(noun, verb) {
  def data = new File('groovy/day02/input.txt').text.split(",").collect { it as int }
  data[1] = noun
  data[2] = verb
  return data
}

// write intcode computer.
// code 1 looks at the position indicated in the next two positions, and places their sum in the position indicated b the third positon.
// code 2 does the same with their product.
// code 99 halts
// also changed input[1] with 12, and [2] with 2.

println "Part One:"

def intcode (ops) {
  ptr = 0

  while (ops[ptr] != 99) {
    switch(ops[ptr]){
      case 1:
        // addition
        ops[ops[ptr+3]] = ops[ops[ptr+1]] + ops[ops[ptr+2]]
        break
      case 2:
        // multiplication
        ops[ops[ptr+3]] = ops[ops[ptr+1]] * ops[ops[ptr+2]]
        break
      default:
        println "opcode not recognized"
    }

    ptr += 4 // increment to next instruction
  }

  return ops
}

println "turned off temp" // intcode(setData(12, 2))[0]


// part two
// https://adventofcode.com/2019/day/2#part2
// my ans: 4925

// find the values for ops[1] (noun) and ops[2] (verb) s/t result is desired_output
// values are in range (0, 99)
println "Part Two:"

def findInput(){
  goal = 19690720

  for(int noun = 0;noun<100;noun++) {
    for(int verb = 0;verb<100;verb++){
      if (intcode(setData(noun, verb))[0] == goal) {
        return 100 * noun + verb
      }
    }
  }
  return "${goal} not found."
}

println findInput()
