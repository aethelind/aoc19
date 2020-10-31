// part one
// https://adventofcode.com/2019/day/8
// my ans: 1584

// each number represents a pixel on a layer
// The image you received is 25 pixels wide and 6 pixels tall.
// find the layer that contains the fewest 0 digits.
// On that layer, what is the number of 1 digits multiplied by the number of 2 digits?

def image = new File('groovy/day08/input.txt').text

def get_layers(image){
  def digit_tracker = [:] // layer # : [#0s: x, #1s: y, ...]
  def layer_count = 0
  def pixel_count = 0

  // for each layer, go thru and count the number of 0s, 1s, 2s
  for (pixel in image) {
    if ("012".contains(pixel)) {
      if ((pixel_count%150) == 0) {
        layer_count++
        digit_tracker[layer_count] = ['0':0, '1':0, '2':0]
      }
      pixel_count++
      digit_tracker[layer_count][pixel]++
    }
  }
  return digit_tracker
}

// find the layer with the least 0s
def min_layer = get_layers(image).min { it.value['0'] }
print "Part One: "
// and multiply the # of 1s by # of 2s
println min_layer.value['1'] * min_layer.value['2']

// part two
// https://adventofcode.com/2019/day/8#part2
// my ans: KCGEC

// determine what the images says given that..I
  // layers cover one another, with 1 on top and 100 on bottom
  // 0 is black, 1 is white, and 2 is transparent.
  // transparent pixels show whatever lies behind
  // and then whatever shows up first, white or black, is the colour of that pixel

def get_pixels(image){
  def rows = 25
  def cols = 6

  def pixel_count = 0
  def pixels = [:]

  for (pixel in (0..(rows*cols))){
    pixels[pixel] = []
  }

  // the goal is [(pixel 0) : [layer1, layer2, layer3,...], (pixel 2) : ...]
  for (pixel in image) {
    if ("012".contains(pixel)) {
      if ((pixel_count%(rows*cols)) == 0) {
        pixel_count = 0
      }

      pixels[pixel_count] << pixel
      pixel_count++
    }
  }

  def decoded_image = new String[(rows*cols)]
  // go thru each pixel, and find the first layer that has a 0 or 1
  // then colour that pixel appropriately for the decoded image
  for (pixel in pixels) {
    def flag = true
    for (layer in pixel.value) {
      if ((layer == '0') && flag) {
        flag = false
        decoded_image[pixel.key] = " "
      } else if ((layer == '1') && flag) {
        flag = false
        decoded_image[pixel.key] = "#"
      }
    }
  }

  return decoded_image
}

// printer for message
def format_image(decoded_image){
  rows = 25
  cols = 6
  for (i in (0..(rows*cols)-1)){
    if ((i%rows == 0) && (i!=0)){
      println ""
    }
    print decoded_image[i]
  }
  println ""
}

println "Part Two: "
format_image(get_pixels(image))
println "KCGEC"

/*
#  #  ##   ##  ####  ##
# #  #  # #  # #    #  #
##   #    #    ###  #
# #  #    # ## #    #
# #  #  # #  # #    #  #
#  #  ##   ### ####  ##
*/
