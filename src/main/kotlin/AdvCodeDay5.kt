import kotlin.concurrent.fixedRateTimer

class AdvCodeDay5 {
    var utils = Utils()

    public fun del1(){
        var seeds= SeedsThree()
        var firstLine=true
        var productMap= ProductMap()
        var productList= ProductMapList()
        var locations = emptyList<LongRange>()
        //var locations = emptyList<Long>()
        utils.getFile("dec5.txt").forEachLine {
            if(firstLine) {
                seeds.addSeeds(it.split(":").last())
                firstLine = false;
            }
            else{
                if(!it.isNullOrBlank() && it.first().isLetter()){
                    productMap.addNames(it.split(" ").first())
                }
                if(!it.isNullOrBlank()  && it.first().isDigit()){
                    productMap.addMappings(it)
                }
                if(it.isNullOrBlank()){
                    productList.productMapList += productMap
                    productMap = ProductMap()
                }
            }

        }
        productList.productMapList += productMap
        seeds.range.forEach{
            locations+=getMappingBySeedRange(it,productList)
            println("Location done: " + it.first)
            println(locations.size)
        }

        var min= emptyList<Long>()

        var num = locations.get(0).first
        locations = locations.filter { it.first<=num }
        println("done with all")
        locations.forEach{
            min+=it.first
        }
        println(min.minOrNull())

    }

    class Seeds {
        var seeds: List<Long> = emptyList()
        fun addSeeds(seedsString: String){
            seeds = seedsString.split(" ").filter { !it.isEmpty() }.map { it.toLong() }
        }
    }
    class SeedsTwo {

        var seeds: List<Long> = emptyList()
        fun addSeeds(seedsString: String){
            var seedsLong = seedsString.split(" ").filter { !it.isEmpty() }.map { it.toLong() }
            var i = 1
            while(i<=seedsLong.size){
                if(i%2==0){
                    seeds+=(seedsLong.get(i-2)..seedsLong.get(i-2)+seedsLong.get(i-1)).toList()
                    println(seeds.size)
                }
                i++
            }
        }
    }
    class SeedsThree {
        var range = emptyList<LongRange>().toMutableList()
        fun addSeeds(seedsString: String){
            var seedsLong = seedsString.split(" ").filter { !it.isEmpty() }.map { it.toLong() }
            var i = 1
            while(i<=seedsLong.size){
                if(i%2==0){
                    range.add(LongRange(seedsLong.get(i-2),seedsLong.get(i-1)+seedsLong.get(i-2)-1))

                }
                i++
            }
        }
    }
    class ProductMap {
        var sourceName: String = ""
        var destName: String = ""
        var sourceRanges = emptyList<LongRange>().toMutableList()
        var destRanges= emptyList<LongRange>().toMutableList()
        fun addNames(nameRow: String){
            sourceName= nameRow.split("-").first()
            destName= nameRow.split("-").last()
        }
        fun addMappings(mapString: String){

            var source = mapString.split(" ").get(1).toLong()
            var dest = mapString.split(" ").first().toLong()
            var range = mapString.split(" ").last().toLong()

            sourceRanges.add(LongRange(source, range+source))
            destRanges.add(LongRange(dest, range+dest))
        }

        fun getMappingByRange(source: LongRange, seedName: String): List<LongRange> {
            var ranges = emptyList<LongRange>().toMutableList()
            var nums = emptyList<Long>()
            var tempRanges = emptyList<LongRange>().toMutableList()
            var sourceFound = false

            sourceRanges.forEachIndexed(){index, sourcerange ->
                var start = source.first - sourcerange.first+destRanges.get(index).first
                var end = source.last - sourcerange.first+destRanges.get(index).first

                if(source.first >= sourcerange.first && source.last <= sourcerange.last  ){
                    ranges.add(LongRange(start,end))
                    sourceFound = true
                }else if(source.first < sourcerange.first && source.last <= sourcerange.last && source.last > sourcerange.first ){
                    ranges.add(LongRange(destRanges.get(index).first, end))
                    tempRanges.add(LongRange(source.first,sourcerange.first-1))
                    sourceFound = true
                }else if(source.first >= sourcerange.first && source.first < sourcerange.last && source.last > sourcerange.last ){
                    ranges.add(LongRange(start, destRanges.get(index).last))
                    tempRanges.add(LongRange(sourcerange.last+1,source.last))
                    sourceFound = true
                }else if(source.first < sourcerange.first && source.last > sourcerange.last ){
                    ranges.add(LongRange(destRanges.get(index).first, destRanges.get(index).last))
                    tempRanges.add(LongRange(source.first,sourcerange.first-1))
                    tempRanges.add(LongRange(sourcerange.last+1,source.last))
                    sourceFound = true
                } else {
                    //
                }
                //tempRanges.forEach{
                //    ranges+=getMappingByRange(it, seedName)
                //}
            }
            tempRanges.forEach{
                ranges+=getMappingByRange(it, seedName)
            }
            if(!sourceFound) {ranges.add(source)}

            return ranges


        }
    }
    class ProductMapList{
        var productMapList = emptyList<ProductMap>()
        fun getBySource(source: String): ProductMap {
            return productMapList.filter { it.sourceName==source }.first()
        }
    }

    fun getMappingBySeedRange(seed: LongRange, productMapList: ProductMapList): List<LongRange>{
        var name="seed"
        var currentSource = seed;
        var ranges = productMapList.getBySource(name).getMappingByRange(currentSource, name)
        while(name!="location"){
            var productMap= productMapList.getBySource(name)
            var t = emptyList<LongRange>()
            if(name != "seed"){
                println("Working with seed " + productMap.destName)
                ranges.stream().forEach{t+=productMap.getMappingByRange(it,productMap.destName) }
                //var r = emptyList<LongRange>().toMutableList()
                //t.forEach{r.add(LongRange(it.first,it.first))}
                ranges = t
            }
            println("Ranges size " + ranges.size)
            name=productMap.destName
        }

        // println(currentSource)
        //var no = if(!ranges.isEmpty()) {ranges.get(0).first} else 0
        var min= ranges.get(0).first
        return ranges.filter { it.first <= min }

        //return ranges
        //return ranges
    }


}