// part one
// https://adventofcode.com/2019/day/5
// my ans: _

// write intcode computer.
// code 1 looks at the position indicated in the next two positions, and places their sum in the position indicated b the third positon.
// code 2 does the same with their product.
// code 99 halts

// new:
// code 3 takes an input and saves it in the location given by its parameter. eg. 3,50
// code 4 gives an output, its only parameter
// these inputs and outputs are given.
// add support for immediate mode 1, where a parameter is interpreted as a value rather than a place.
//

// get input
// def range = new File('groovy/day04/input.txt').text.split("-").collect { it as int }

def parseOpcode (op) {
  // ABCDE
  // DE = two digit opcode
  // ABC may be missing preceding zeros

  while (op.size() < 5) {
    op = "0" + op
  }
  return [op[0], op[1], op[2], op[3..4]]
}

println parseOpcode(1002 as String)

def intcode (ops) {
  ptr = 0
  inputParam = 1

  while (ops[ptr] != 99) {
    switch(ops[ptr]){
      case 1:
        // addition
        ops[ops[ptr+3]] = ops[ops[ptr+1]] + ops[ops[ptr+2]]
        ptr += 4
        break
      case 2:
        // multiplication
        ops[ops[ptr+3]] = ops[ops[ptr+1]] * ops[ops[ptr+2]]
        ptr += 4
        break
      case 3:
        println ""
        ptr += 2
        break
      case 4:
        println ops[ptr] // should be zero
        ptr += 2
        break
      default:
        println "opcode not recognized"
    }


  }

  return ops
}


print "Part One: "
println "ans"


print "Part Two: "
println "ans"
