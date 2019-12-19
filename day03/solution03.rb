# part one
# https://adventofcode.com/2019/day/3
# my ans: _


def manhattan(pt_a, pt_b)
	return (pt_a[0] - pt_b[0]).abs + (pt_a[1] - pt_b[1]).abs
end


class Panel
	panel = {[0,0] => :central_port}
	
	# takes position and cmd like L123 and returns updated position
	def Panel.getPos(dirs)
		# set up origin, hash for wire locations, and minimum manhattan distance
		pos = [0,0]
		panel = {[0,0] => :central_port}
		min_man = [Float::INFINITY, [0,0]]

		dirs.each do |cmds|
			cmds.split(",").each do |cmd|
				puts cmd
				# get direction info
				delta = cmd.delete("^0-9").to_i
				i = -1

				case cmd[0]
				when 'U'
					i = 1
					op = :+
				when 'D'
					i = 1
					op = :-
				when 'L'
					i = 0
					op = :-
				when 'R'
					i = 0
					op = :+
				else
					raise "no direction: " + cmd	
				end

				# plod along in that direction, delta times
				delta.times { 
					pos[i] = pos[i].send(op, 1) 
					if panel.keys.include?(pos.clone)
						# if this is an intersection, check if manhattan is smaller than current minimum
						if manhattan(pos, [0,0]) < min_man[0]
							min_man[0] = manhattan(pos, [0,0])
							min_man[1] = pos.clone
						end
					else
						panel[pos.clone] = :visited 
					end
				}
			end
		end
		
		return min_man[0]
	end
end

# get the wires wire[0] and wire[1]
wires = File.open("input.txt").readlines
puts wires[0]

# puts Panel.getPos([0,0], [["R8","U5","L5","D3"], ["U7","R6","D4","L4"]])
puts Panel.getPos(wires)
