// part one
// https://adventofcode.com/2019/day/5
// my ans: 7988899

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

def parseOpcode (op) {
  // if op is ABCDE
  // DE = two digit opcode
  // C = 1st param mode, B = 2nd param mode, A = 3rd param mode
  // ABC may be missing preceding zeros

  // get all three modes
  while (op.size() < 5) {
    op = "0" + op
  }

  return [op[2].toBoolean(), op[1].toBoolean(), op[0].toBoolean(), op[3..4] as int]
}

def intcode (ops, input) {
  ptr = 0
  output = 0

  while (true) {
    op = parseOpcode(ops[ptr] as String)

    if (op[3] == 99) { break }

    a = op[0] ? ptr + 1 : ops[ptr+1]
    b = op[1] ? ptr + 2 : ops[ptr+2]
    c = op[2] ? ptr + 3 : ops[ptr+3]

    switch(op[3]){
      case 1:
        // addition
        ops[c] = ops[a] + ops[b]
        ptr += 4
        break
      case 2:
        // multiplication
        ops[c] = ops[a] * ops[b]
        ptr += 4
        break
      case 3:
        // set input value
        ops[a] = input
        ptr += 2
        break
      case 4:
        // get output value
        output = ops[a]
        ptr += 2
        break
      case 5:
        // jump-if-true
        if (ops[a]) {
          ptr = ops[b]
        } else {
          ptr += 3
        }
        break
      case 6:
        // jump-if-false
        if (!ops[a]) {
          ptr = ops[b]
        } else {
          ptr += 3
        }
        break
      case 7:
        // less than
        ops[c] = (ops[a] < ops[b]) ? 1 : 0
        ptr += 4
        break
      case 8:
        // equals
        ops[c] = (ops[a] == ops[b]) ? 1 : 0
        ptr += 4
        break
      default:
        throw new Exception("Opcode '${ops[ptr]}' not recognized")
    }
  }

  return output
}

print "Part One: "
def data = new File('groovy/day05/input.txt').text.split(",").collect { it as int }
println intcode(data, 1)

// part two
// https://adventofcode.com/2019/day/5#part2
// my ans: 13758663

// new:
// code 5: jump-if-true, if first param is non-zero, set the instruction pointer to the value from the second param.
// code 6: jump-if-false, if first param is zero, set the instruction pointer to the value from the second param.
// code 7: less than, if the first param is less than the second param, store 1 in the pos given by the third param, else store 0.
// code 8: equals, if the first param is equal to the second param, store 1 in the pos given by the third param, else store 0.

print "Part Two: "
data = new File('groovy/day05/input.txt').text.split(",").collect { it as int }
println intcode(data, 5)
