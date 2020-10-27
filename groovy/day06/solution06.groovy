// part one
// https://adventofcode.com/2019/day/6
// my ans: 106065

// get number of direct and indirect orbits

// def orbits = new File('groovy/day06/input.txt').text.split("\n")
def orbits = new File('groovy/day06/input.txt').text.split("\\r\\n|\\n|\\r").collect { it.split("\\)") }

// nodes = objects that orbit other objects
class Node {
   public int steps;
   public String name;
   public Node parent;
   public children;

    @Override
    String toString() {
      return name
      //return name == "COM" ? "COM orbits nothing" :"${name} orbits ${parent.name}"
    }

   Node(int steps, String name, Node parent){
     this.steps = steps
     this.name = name
     this.parent = parent
     this.children = []
   }
}

// list which contains nodes.
class NodeList {
   public nodes;

   @Override
   String toString() {
     nodes.each { println it }
     return ""
   }

   void add(Node n){
     nodes[n.name] = n
   }

   Node getNode(String n) {
     return nodes[n]
   }

   NodeList(){
     this.nodes = [:]
   }
}

def setupNodeList(orbits){
  def nl = new NodeList()

  // instantiate the parent node
  nl.add(new Node(0, "COM", null))

  // make a node for each object that orbits something
  for (o in orbits) {
    nl.add(new Node(0, o[1], null))
  }

  for (o in orbits) {
    // connect each child to its parent
    nl.getNode(o[1]).parent = nl.getNode(o[0])
    // connect each parent to its child(ren)
    nl.getNode(o[0]).children.add(nl.getNode(o[1]))
  }

  return nl
}

def dfs(nl) {
  def num_orbits = 0
  // create dfs stack
  def visit = []
  // instantiate stack with children of top level parent
  nl.getNode("COM").children.each() {  visit.push(it) }

  while (visit.size() != 0) {
    curr = visit.pop() // get current node
    curr.steps = curr.parent.steps + 1 // increment step count of node
    num_orbits += curr.steps // include step count of this node in orbit count
    curr.children.each {  visit.push(it)  } // add this node's children to visit
  }

  return num_orbits
}

print "Part One: "
def nl = setupNodeList(orbits)
println dfs(nl)

// part two
// https://adventofcode.com/2019/day/6#part2
// my ans: 253

// min # of moves to go from YOU's parent to SAN's parent?
// (Between the objects they are orbiting - not between YOU and SAN.)

def getHier(leaf) {
  def path = []
  def steps = 0

  while (leaf.parent.name != "COM") {
    path[steps++] = leaf.parent
    leaf = leaf.parent
  }

  return path
}

def commonParent(san_path, you_path){
  for (i=0;i<san_path.size();i++){
    for (j=0;j<you_path.size();j++){
      if (san_path[i] == you_path[j]){
        return i + j
      }
    }
  }
}

print "Part Two: "
println commonParent(getHier(nl.getNode("SAN")), getHier(nl.getNode("YOU")))
