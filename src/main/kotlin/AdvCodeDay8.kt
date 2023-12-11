import java.io.File

class AdvCodeDay8 {
    var utils = Utils()
    var directions: String = ""
    var paths = PathEntries2()
    var startPoint = "AAA"
    var endPoint = "ZZZ"
    var startPositions = emptyList<String>()

    fun del1(){
        readFile()
        executeDirections()
    }
    fun del2(){
       readFile()
       startPositions = findStartPositions()
       executeDirections3()

    }
    fun readFile(){
        var first = true
        utils.getFile("dec8.txt").forEachLine {
            var row = it.filter { !it.isWhitespace() }.filter { it != '(' }.filter { it != ')' }
            if(first){
              directions = it
              first = false
            } else if(row.isNotEmpty() && row.first().isLetter()){
                var r = row.split("=")
                if(startPoint.isBlank()) startPoint = r.get(0)
                var dirs = r.get(1).split(",")
                paths.entries+= mapOf(r.get(0) to PathEntry(r.get(0),dirs.get(0),dirs.get(1)))
            }
        }
    }
    fun findStartPositions():List<String> {
        return paths.entries.filter { it.key.endsWith("A") }.map { it.key }
    }
    class PathEntry(val id: String, val left: String,val right: String){
          val choice = mapOf('L' to left, 'R' to right)
    }
    fun executeDirections(){
        var index = 0
        var steps:Long = 0
        while(startPoint != endPoint){
            startPoint = paths.getNext(startPoint,directions.get(index))

            index++
            steps++
            if(index == directions.length) {
                index = 0
                println("One iteration steps are now" + steps)
            }
        }
        println(startPoint)
        println(steps)
    }
    class TempPlace(var id: String,var index: Int,var steps: Long)
    fun executeDirectionsTemp(t: TempPlace): TempPlace{
        var index = t.index
        var steps:Long = 0
        var sPoint = "Q"
        var first = true
        while(sPoint.last() != 'Z'){

            sPoint = paths.getNext(if(first) t.id else sPoint,directions.get(index))
            first = false
            index++
            steps++
            if(index == directions.length) {
                index = 0
            }
        }
        t.id = sPoint
        t.index = index
        t.steps+=steps
        return t
    }
    fun executeDirections2(){
        var index = 0
        var steps:Long = 0
        var iterationResult = "QQQQQ"
        var expectedResult = "".padEnd(startPositions.size, 'Z')
        while(iterationResult != expectedResult){
        //while(!iterationResult.contains("ZZZ")){
            var t = emptyList<String>()
            iterationResult=""
            startPositions.forEach{
               var pos = paths.getNext(it,directions.get(index))
               t+=pos
               iterationResult+=pos.last()
            }
            startPositions = t
            index++
            steps++
            if(index == directions.length) {
                index = 0
                //println(iterationResult)
            }
        }
        println(iterationResult)
        println(steps)
    }
    fun executeDirections3(){

        var allResult = emptyList<TempPlace>().toMutableList()
        startPositions.forEach { allResult.add(executeDirectionsTemp(TempPlace(it,0,0))) }
        var found = false
        while(!found){
            var lowest = allResult.map { it.steps }.minOrNull()?:0
            if(allResult.map { it.steps }.minOrNull() == allResult.map { it.steps }.maxOrNull()){
                found = true
            }
            else{
                executeDirectionsTemp(allResult.filter { it.steps == lowest }.get(0) )
            }

        }
        println(allResult.get(0).id)
        println(allResult.get(0).steps)
    }
    
    class PathEntries(){
        var entries: List<PathEntry> = emptyList<PathEntry>().toMutableList()
        fun getNext(id : String, direction: Char): String{
            return entries.firstOrNull{ it.id == id }?.choice?.get(direction)?:""
             //return entries.filter { it.id == id }.get(0).choice.get(direction)?:""
        }
    }
    class PathEntries2(){
        var entries: Map<String,PathEntry> = HashMap<String,PathEntry>()
        fun getNext(id : String, direction: Char): String{
            return entries.get(id)?.choice?.get(direction)?:""
            //return entries.filter { it.id == id }.get(0).choice.get(direction)?:""
        }
    }





}