import javax.swing.text.StyledEditorKit.BoldAction

class AdvCodeDay4 {
    var utils = Utils()
    fun isWinner(myNumber : String, winners: List<String>): Boolean{
        return myNumber in winners
    }
    public fun del1(){
        var totalPoints = 0;
        utils.getFile("dec4.txt").forEachLine {
            var cardNo = it.split(":")[0].filter { it.isDigit()}
            var winners = it.split(":")[1].split("|")[0].split(" ").filter { !it.isEmpty() }
            var myNumbers = it.split(":")[1].split("|")[1].split(" ").filter { !it.isEmpty() }
            var pointsForCard = 0;
            myNumbers.forEach{
                if(isWinner(it,winners)){
                    if(pointsForCard == 0){
                        pointsForCard = 1;
                    }else{
                        pointsForCard = pointsForCard.times(2)
                    }
                }
            }
            totalPoints += pointsForCard;
            pointsForCard=0;

        }
        println(totalPoints)

    }
    public fun del2(){
        var totalPoints = 0;
        var cardInstances = CardInstances()
        utils.getFile("dec4.txt").forEachLine {
            var cardNo = it.split(":")[0].filter { it.isDigit()}
            var winners = it.split(":")[1].split("|")[0].split(" ").filter { !it.isEmpty() }
            var myNumbers = it.split(":")[1].split("|")[1].split(" ").filter { !it.isEmpty() }
            var numberOfWinners = 0;
            myNumbers.forEach{
                if(isWinner(it,winners)){
                   numberOfWinners++
                }
            }
            addWinner(cardNo, numberOfWinners,cardInstances)
            numberOfWinners=0;
        }
        println(cardInstances.sumCopies())

    }

    fun addWinner(card: String, noOfWinners: Int, cards: CardInstances){
        var cardInt = Integer.parseInt(card)
        cards.getInstanceById(cardInt).copies++
        var currentCopiesOfCard = cards.getInstanceById(cardInt).copies

        for(i in cardInt+1 until noOfWinners+cardInt+1){
            cards.getInstanceById(i).copies+= currentCopiesOfCard
        }
    }
    class CardInstance(
        var id: Int,
        var copies: Int) {
    }
    class CardInstances {
        var instances: List<CardInstance> = emptyList<CardInstance>()
        fun getInstanceById(id: Int) : CardInstance {
            val filteredInstances = instances.filter { it.id.equals(id)}
            if(filteredInstances.size == 0){
                val newCardInstance = CardInstance(id,0)
                instances += newCardInstance
                return newCardInstance
            }
            return filteredInstances.first()
        }
        fun sumCopies():Int{
            var sumOfCopies = 0
            instances.forEach{
                sumOfCopies+= it.copies
            }
            return sumOfCopies
        }
    }

}