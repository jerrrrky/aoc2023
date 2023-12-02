import java.io.File

class AdvCodeDay1 {
    public fun del1(){
        var total = 0;
        File("src/main/resources/dec1.txt").forEachLine {
            var row = it.filter { it.isDigit()}
            var num = "";
            if(row.length == 1){
                num = row + row
            } else if (row.length > 1){
                num = row.first().toString() + row.last().toString()
            }
            println(num)
            total += Integer.parseInt(num)
        }
        println(total)
    }
    fun del2(){
        var total = 0;
        var numbers = arrayOf("zero","one","two","three","four","five","six","seven","eight","nine","0","1","2","3","4","5","6","7","8","9")
        File("src/main/resources/dec1.txt").forEachLine {
            var current = StringBuilder(it);
            var num = "";
            numbers.forEachIndexed { index,n ->
                if(index < 10){
                    if(current.toString().contains(n)){
                        current.insert(current.toString().indexOf(n) , n+numbers.get(index+10)+n)
                        current.insert(current.toString().lastIndexOf(n) , n+numbers.get(index+10)+n)
                    }
                }
            }
            var row = current.toString().filter { it.isDigit()}
            if(row.length == 1){
                num = row + row
            } else if (row.length > 1){
                num = row.first().toString() + row.last().toString()
            }
            println(num)
            total += Integer.parseInt(num)
        }
        println(total)
    }
}