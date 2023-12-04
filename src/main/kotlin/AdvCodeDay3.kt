import java.io.File

class AdvCodeDay3 {

    fun findAllNumbersInRow(row: String): List<FoundNumber>{
        var nums: List<FoundNumber> = emptyList<FoundNumber>()
        var foundNumber = "";
        var startindex = 0
        for(i in 0..row.length-1){
            if(row[i].isDigit()){
                if(foundNumber == ""){
                    startindex = i;
                }
                foundNumber += row[i].toString()
            }
            if((!row[i].isDigit() or i.equals(row.length-1)) and (!foundNumber.equals(""))){
                var found = FoundNumber();
                found.setNumber(Integer.parseInt(foundNumber),startindex,i-1)
                nums +=  found
                startindex= 0
                foundNumber=""
            }
        }
        return nums
    }
    fun findAllSpecialsInRow(row: String): List<Int>{
        var indexes: List<Int> = emptyList<Int>()
        for(i in 0..row.length-1){
            if(!row[i].isLetterOrDigit() and !row[i].equals('.')){
                indexes += i
            }

        }
        return indexes
    }
    fun findAllStarsInRow(row: String): List<Int>{
        var indexes: List<Int> = emptyList<Int>()
        for(i in 0..row.length-1){
            if(row[i].equals("*")){
                indexes += i
            }

        }
        return indexes
    }
    fun sumRow(row: List<FoundNumber>, before: List<Int>, same: List<Int>, after: List<Int>): Int{

        var sum = 0
        row.forEach{
            var found = false
            var num = it
            before.forEach {
                if(it in num.startIndex-1..num.endIndex+1){
                    if(!found){
                        sum+=num.sum
                    }
                    found= true;
                    return@forEach
                }
            }
            if(!found){
                same.forEach {
                    if(it in num.startIndex-1..num.endIndex+1){
                        if(!found){
                            sum+=num.sum
                        }
                        found= true;
                        return@forEach
                    }
                }
            }
            if(!found){
                after.forEach {
                    if(it in num.startIndex-1..num.endIndex+1){
                        if(!found){
                            sum+=num.sum
                        }
                        found= true;
                        return@forEach
                    }
                }
            }
        }

        return sum;
    }
    fun sumRowStars(row: List<FoundNumber>, before: List<Int>, same: List<Int>, after: List<Int>): Int{

        var sum = 0
        row.forEach{
            var found = false
            var num = it
            before.forEach {
                if(it in num.startIndex-1..num.endIndex+1){
                    if(!found){
                        sum+=num.sum
                    }
                    found= true;
                    return@forEach
                }
            }
            if(!found){
                same.forEach {
                    if(it in num.startIndex-1..num.endIndex+1){
                        if(!found){
                            sum+=num.sum
                        }
                        found= true;
                        return@forEach
                    }
                }
            }
            if(!found){
                after.forEach {
                    if(it in num.startIndex-1..num.endIndex+1){
                        if(!found){
                            sum+=num.sum
                        }
                        found= true;
                        return@forEach
                    }
                }
            }
        }

        return sum;
    }
    var utils = Utils()
    public fun del1() {

        var total = 0;
        var row1 = emptyList<FoundNumber>()
        var row2 = emptyList<FoundNumber>()
        var row3 = emptyList<FoundNumber>()
        var indexes1 = emptyList<Int>()
        var indexes2 = emptyList<Int>()
        var indexes3 = emptyList<Int>()
        var rowNum = 0;
        var numOfRows = 0
        utils.getFile("dec3.txt").forEachLine {
            numOfRows ++
        }
        println("Antal rader"+numOfRows)
        utils.getFile("dec3.txt").forEachLine {
            row3=row2;
            row2=row1
            row1=findAllNumbersInRow(it)
            indexes3=indexes2;
            indexes2=indexes1
            indexes1=findAllSpecialsInRow(it)
            if(rowNum == 2){
                total += sumRow(row3, emptyList<Int>(), indexes3, indexes2)
                total += sumRow(row2, indexes3, indexes2, indexes1)
            }
            if(rowNum > 2 && rowNum < numOfRows-1){
                total += sumRow(row2, indexes3, indexes2, indexes1)
            }
            if(rowNum > 2 && rowNum == numOfRows-1){
                total += sumRow(row2, indexes3, indexes2, indexes1)
                total += sumRow(row1, indexes2, indexes1,emptyList<Int>())
            }
           rowNum ++;
        }
        println(total)
    }

    class FoundNumber {
        var sum: Int = 0
        var startIndex = 0
        var endIndex = 0
        fun setNumber(sum: Int, start: Int, end: Int){
            this.sum = sum
            this.startIndex = start
            this.endIndex = end
        }
    }
}