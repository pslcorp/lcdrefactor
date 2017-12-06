require 'pry'

class LCD
  # _ -> Horizontal
  # | -> Vertical
  attr_accessor(:size, :spacing)

  #
  # This hash is used to define the segment display for the
  # given digit. Each entry in the array is associated with
  # the following states:
  #
  #    HORIZONTAL
  #    VERTICAL
  #    HORIZONTAL
  #    VERTICAL
  #    HORIZONTAL
  #    DONE
  #
  # The HORIZONTAL state produces a single horizontal line. There
  # are two types:
  #
  #    0 - skip, no line necessary, just space fill
  #    1 - line required of given size
  #
  # The VERTICAL state produces a either a single right side line,
  # a single left side line or a both lines.
  #
  #    0 - skip, no line necessary, just space fill
  #    1 - single right side line
  #    2 - single left side line
  #    3 - both lines
  #
  # The DONE state terminates the state machine. This is not needed
  # as part of the data array.
  #
  #
  def initialize(spacing = 1)
    @spacing = spacing
  end

  def input
    puts 'Ingrese tamaño de letra y numeros a imprimir en pantalla, termine con 0,0'
    puts 'Ejm: 1,1234 0,0'
    puts ''
    STDOUT.flush
    arr_input(gets.chomp)
  end

  def arr_input(input)
    arr_input = input.split
    arr_input.each do |value|
      return if value.eql? '0,0'
      validate_value(value)
    end
  end

  def validate_value(value)
    value = value.split(',')
    # binding.pry
    return unless (value.size.eql? 2) && value[0].to_i.integer? && value[0].to_i != 0 && (value[0].to_i != (1..10)) && value[1].to_i.integer?
    display(value[0].to_i, value[1].to_s)
  rescue
    ''
  end

  def lcdDisplayData
    @lcdDisplayData = {
      '0' => [1, 3, 0, 3, 1],
      '1' => [0, 1, 0, 1, 0],
      '2' => [1, 1, 1, 2, 1],
      '3' => [1, 1, 1, 1, 1],
      '4' => [0, 3, 1, 1, 0],
      '5' => [1, 2, 1, 1, 1],
      '6' => [1, 2, 1, 3, 1],
      '7' => [1, 1, 0, 1, 0],
      '8' => [1, 3, 1, 3, 1],
      '9' => [1, 3, 1, 1, 1]
    }
  end

  def lcdStates
    @lcdStates = %w[
      DONE
      HORIZONTAL
      VERTICAL
      HORIZONTAL
      VERTICAL
      HORIZONTAL
    ]
  end

  def display(size, digits)
    states = lcdStates
    0.upto(lcdStates.length) do |state|
      case states.pop
      when 'HORIZONTAL'
        line = ''
        digits.each_byte do |digit|
          line += horizontal_segment(lcdDisplayData[digit.chr][state], size)
        end
        print line + "\n"
      when 'VERTICAL'
        1.upto(size) do |_j|
          line = ''
          digits.each_byte do |digit|
            line += vertical_segment(lcdDisplayData[digit.chr][state], size)
          end
          print line + "\n"
        end
      when 'DONE'
        break
      end
    end
  end

  def horizontal_segment(type, size)
    case type
    when 1
      ' ' + ('-' * size) + ' ' + (' ' * @spacing)
    else
      ' ' + (' ' * size) + ' ' + (' ' * @spacing)
    end
  end

  def vertical_segment(type, size)
    case type
    when 1
      ' ' + (' ' * size) + '|' + (' ' * @spacing)
    when 2
      '|' + (' ' * size) + ' ' + (' ' * @spacing)
    when 3
      '|' + (' ' * size) + '|' + (' ' * @spacing)
    else
      ' ' + (' ' * size) + ' ' + (' ' * @spacing)
    end
  end
end

start = LCD.new
start.input
