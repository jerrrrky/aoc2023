import java.io.File

class AdvCodeDay2 {
    fun isOk(color: String, number: Int): Boolean {
        if(color.equals("green").and(number <= 13)){
            return true;
        }
        if(color.equals("red").and(number <= 12)){
            return true;
        }
        if(color.equals("blue").and(number <= 14)){
            return true;
        }
        return false
    }

    public fun del1() {

        var total = 0;
        File("src/main/resources/dec2.txt").forEachLine {

            var gameNo= it.split(":")[0].filter { it.isDigit() }
            var colors = it.split(":")[1].split(";")
            var ok = true
            colors.forEach {
                it.split(",").forEach {
                    if(!isOk(it.filter { it.isLetter() }.toString(), Integer.parseInt(it.filter { it.isDigit() }))){
                        ok = false
                    }
                }
            }
            if(ok){
                println(gameNo + " OK")
                total += Integer.parseInt(gameNo)
            }
        }
        println(total)
    }
    public fun del2() {

        var total = 0;
        File("src/main/resources/dec2.txt").forEachLine {
            var colors = it.split(":")[1].split(";")
            var green = 1
            var blue = 1
            var red = 1
            colors.forEach {
                it.split(",").forEach {
                    var currentColor = it.filter { it.isLetter() }.toString()
                    var currentBags = Integer.parseInt(it.filter { it.isDigit() })
                    if("green".equals(currentColor, false)){
                        if(green < currentBags) green = currentBags
                    }
                    if("red".equals(currentColor, false)){
                        if(red < currentBags) red = currentBags
                    }
                    if("blue".equals(currentColor, false)){
                        if(blue < currentBags) blue = currentBags
                    }
                }
            }
            total += green*blue*red
            println(total)
        }
        println(total)
    }
}