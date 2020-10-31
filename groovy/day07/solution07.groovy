// part one
// https://adventofcode.com/2019/day/7
// my ans: 47064

// adapted intcode computer from solution05
class Amplifier {
  def ptr
  def output
  def flag
  def ops
  def isHalted

  static def parseOpcode (op) {
    while (op.size() < 5) {
      op = "0" + op
    }

    return [op[2].toBoolean(), op[1].toBoolean(), op[0].toBoolean(), op[3..4] as int]
  }

  def intcode (inputs) {
    while (true) {
      def op = parseOpcode(ops[ptr] as String)

      if (op[3] == 99) {
        isHalted = true
        return "halt"
      }

      def a = op[0] ? ptr + 1 : ops[ptr+1]
      def b = op[1] ? ptr + 2 : ops[ptr+2]
      def c = op[2] ? ptr + 3 : ops[ptr+3]

      switch(op[3]){
        case 1:
          ops[c] = ops[a] + ops[b]
          ptr += 4
          break
        case 2:
          ops[c] = ops[a] * ops[b]
          ptr += 4
          break
        case 3:
          if (flag) {
            flag = false
            ops[a] = inputs[0] // phase setting 01234 / 56789
          } else {
            ops[a] = inputs[1] // input signal from last amplifier
          }
          ptr += 2
          break
        case 4:
          output = ops[a]
          ptr += 2
          return output
          break
        case 5:
          if (ops[a]) {
            ptr = ops[b]
          } else {
            ptr += 3
          }
          break
        case 6:
          if (!ops[a]) {
            ptr = ops[b]
          } else {
            ptr += 3
          }
          break
        case 7:
          ops[c] = (ops[a] < ops[b]) ? 1 : 0
          ptr += 4
          break
        case 8:
          ops[c] = (ops[a] == ops[b]) ? 1 : 0
          ptr += 4
          break
        default:
          throw new Exception("Opcode '${ops[ptr]}' not recognized")
      }
    }

    return "halt"
  }

  Amplifier(){
    this.ptr = 0
    this.output = 0
    this.flag = true
    this.ops = new File('groovy/day07/input.txt').text.split(",").collect { it as int }
    this.isHalted = false
  }
}

// there are amplifiers A thru E.
// each produces an output which gets fed as input into the next amplifier (the first input is 0)
// amp E's output is the signal
// they take a second input, which is 0 thru 4. each amp gets a unique second input (phase)
// what is the highest possible signal, trying out each permutation of 0..4 as inputs?
def runAmplifiers(phases){
  //phases = [0, 1, 2, 3, 4] in order for A B C D E
  amplifiers = [(new Amplifier()), (new Amplifier()), (new Amplifier()), (new Amplifier()), (new Amplifier())]

  signals = ['a', 'b', 'c', 'd', 0]

  signals[0] = amplifiers[0].intcode([phases[0], signals[4]]) // A
  signals[1] = amplifiers[1].intcode([phases[1], signals[0]]) // B
  signals[2] = amplifiers[2].intcode([phases[2], signals[1]]) // C
  signals[3] = amplifiers[3].intcode([phases[3], signals[2]]) // D
  signals[4] = amplifiers[4].intcode([phases[4], signals[3]]) // E

  return signals[4]
}

def max = -1

[0, 1, 2, 3, 4].eachPermutation { setting ->
  signal = runAmplifiers(setting)
  if (signal > max) {
    max = signal
  }
}
assert max == 47064
print "Part One: "
println max


// part two
// https://adventofcode.com/2019/day/7#part2
// my ans: 4248984

// feedback loop. now, the amplifiers continue to feed into one another
// eg. amplifier E's input feeds into amplifier abstract
// the amplifiers continue on a loop until a halt command is run
// also the phases are 5,6,7,8,9
// and try to find max signal
def runAmplifiersFeedback(phases){
  //phases = [5, 6, 7, 8, 9] in some order for A B C D E

  amplifiers = [(new Amplifier()), (new Amplifier()), (new Amplifier()), (new Amplifier()), (new Amplifier())]

  signals = ['a', 'b', 'c', 'd', 'e']
  signals[4] = 0

  while (!amplifiers[4].isHalted) {
    signals[0] = amplifiers[0].intcode([phases[0], signals[4]]) // A
    signals[1] = amplifiers[1].intcode([phases[1], signals[0]]) // B
    signals[2] = amplifiers[2].intcode([phases[2], signals[1]]) // C
    signals[3] = amplifiers[3].intcode([phases[3], signals[2]]) // D
    signals[4] = amplifiers[4].intcode([phases[4], signals[3]]) // E
  }

  return amplifiers[4].output
}

max = -1 // reset max

[5, 6, 7, 8, 9].eachPermutation { setting ->
  signal = runAmplifiersFeedback(setting)
  if (signal > max) {
    max = signal
  }
}

assert max == 4248984
print "Part Two: "
println max
