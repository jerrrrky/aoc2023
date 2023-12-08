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
        utils.getFile("dec7ex.txt").forEachLine {
            var hand = Hand(it.split(" ").get(0),it.split(" ").get(1).toLong() )
            hand.setValueHand()
            hands += hand
        }
        return hands

    }

    class Hand(val hand: String, val value: Long): Comparable<Hand>{
        val valueMap = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11,'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2, '1' to 1)
        var valueHand = emptyList<Int>().toMutableList()
        var frequencies = emptyMap<Int,Int>()
        fun setValueHand() {
            hand.forEach {
                valueHand.add(valueMap.get(it) ?: 0)
                frequencies = valueHand.groupingBy { it }.eachCount().toSortedMap()
            }
        }

        override fun compareTo(other: Hand): Int {
            var maxThis = this.frequencies.values.toList().maxOrNull()?: 0
            var maxOther = other.frequencies.values.toList().maxOrNull()?: 0
            if(maxThis != maxOther) return maxThis.compareTo(maxOther)
            if (this.frequencies.size != other.frequencies.size) return this.frequencies.size.compareTo(other.frequencies.size)
            if(this.valueHand.get(0) != other.valueHand.get(0)) return other.valueHand.get(0).compareTo(this.valueHand.get(0))
            if(this.valueHand.get(1) != other.valueHand.get(1)) return other.valueHand.get(1).compareTo(this.valueHand.get(1))
            if(this.valueHand.get(2) >= other.valueHand.get(2)) return other.valueHand.get(2).compareTo(this.valueHand.get(2))
            if(this.valueHand.get(3) >= other.valueHand.get(3)) return other.valueHand.get(3).compareTo(this.valueHand.get(3))
            if(this.valueHand.get(4) >= other.valueHand.get(4)) return other.valueHand.get(4).compareTo(this.valueHand.get(4))
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
        }
        println(value)
    }



    }