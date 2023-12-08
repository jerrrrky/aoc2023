import java.io.File

class AdvCodeDay6 {
    var utils = Utils()

    fun del1(){
        var races = readRaces()
        var total = 1
        races.races.forEach {
           total*=it.calculateWinners()
        }
        println(total)
    }
    fun del2(){
        var race = readRaces2()
        var total = race.calculateWinners()
        println(total)
    }
    fun readRaces(): Races{
        var races = Races()
        var times = emptyList<String>()
        var distances = emptyList<String>()
        utils.getFile("dec6.txt").forEachLine {

            if(it.startsWith("T")){
                times = it.split(":").get(1).split(" ").filter { !it.isNullOrBlank() }
            }else{
                distances = it.split(":").get(1).split(" ").filter { !it.isNullOrBlank() }
            }


        }
        times.forEachIndexed{index, time ->
            races.races.add(Race(time.toLong(),distances.get(index).toLong()))
        }
        return races
    }
    fun readRaces2(): Race{

        var time: Long = 0
        var distance :Long = 0
        utils.getFile("dec6.txt").forEachLine {

            if(it.startsWith("T")){
                time = it.filter { it.isDigit() }.toLong()
            }else{
                distance = it.filter { it.isDigit() }.toLong()
            }
        }
        return Race(time,distance)
    }
    class Race(val time: Long, val distance: Long){
        fun calculateWinners(): Int{
            var winner = 0
            for(i in 1..time){
                if((time - i)*i > distance) winner ++
            }
            return winner
        }
    }
    class Races{
        var races = emptyList<Race>().toMutableList()

    }
}