import java.io.File

class AdvCodeDay7 {
    var utils = Utils()

    fun del1(){
        var hands = readHands()
        var sorted = hands.sorted()

        sum(sorted)

    }
    fun del2(){

    }
    fun readHands(): List<Hand> {
        var hands = emptyList<Hand>()
        utils.getFile("dec7.txt").forEachLine {
            var hand = Hand(it.split(" ").get(0),it.split(" ").get(1).toLong() )
            hand.setValueHand()
            hands += hand
        }
        return hands

    }

    class Hand(val hand: String, val value: Long): Comparable<Hand>{
        //val valueMap = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11,'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2, '1' to 1)
        val valueMap = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 0,'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2, '1' to 1)
        var valueHand = emptyList<Int>().toMutableList()
        var frequencies = emptyMap<Int,Int>()
        fun setValueHand() {
            hand.forEach {
                valueHand.add(valueMap.get(it) ?: 0)
                frequencies = valueHand.groupingBy { it }.eachCount()
            }
        }

        fun findSeconHighestValue(map: Map<Int,Int>):Int{
            var max = map.values.toList().maxOrNull()?:0
            var min = map.values.toList().minOrNull()?:0
            var maxes = 0
            map.forEach{
                if(it.value > min && it.value < max) min = it.value
                if(it.value == max) maxes ++
            }
            if(maxes > 1) return max
            if(max == min) return 0
            return min
        }
        override fun compareTo(other: Hand): Int {
            var maxThis = this.frequencies.filter { it.key != 0 }.values.toList().maxOrNull()?: 0

            var second = findSeconHighestValue(this.frequencies.filter { it.key !=0 })
            var jokerThis = this.frequencies.filter { it.key == 0 }.values.toList().maxOrNull()?: 0

            maxThis += jokerThis
            var maxOther = other.frequencies.filter { it.key != 0 }.values.toList().maxOrNull()?: 0
            var secondOther = findSeconHighestValue(other.frequencies.filter { it.key !=0 })
            var jokerOther = other.frequencies.filter { it.key == 0 }.values.toList().maxOrNull()?: 0
            maxOther+=jokerOther


            if(maxThis != maxOther) return maxThis.compareTo(maxOther)
            if(second != secondOther) return second.compareTo(secondOther)
            //if (this.frequencies.size != other.frequencies.size) return other.frequencies.size.compareTo(this.frequencies.size)
            if(this.valueHand.get(0) != other.valueHand.get(0)) return this.valueHand.get(0).compareTo(other.valueHand.get(0))
            if(this.valueHand.get(1) != other.valueHand.get(1)) return this.valueHand.get(1).compareTo(other.valueHand.get(1))
            if(this.valueHand.get(2) != other.valueHand.get(2)) return this.valueHand.get(2).compareTo(other.valueHand.get(2))
            if(this.valueHand.get(3) != other.valueHand.get(3)) return this.valueHand.get(3).compareTo(other.valueHand.get(3))
            if(this.valueHand.get(4) != other.valueHand.get(4)) return this.valueHand.get(4).compareTo(other.valueHand.get(4))
            return 0
        }

    }
    class Hands {
        var hands = emptyList<Hand>().toMutableList()
    }
    fun sum(sorted: List<Hand>){
        var value :Long = 0
        sorted.forEachIndexed{index, hand ->
            value += (index+1) * hand.value
            println(hand.hand)
        }
        println(value)
    }



    }