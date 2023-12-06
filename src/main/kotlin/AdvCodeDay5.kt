
class AdvCodeDay5 {
    var utils = Utils()

    public fun del1(){
        var seeds= SeedsThree()
        var firstLine=true
        var productMap= ProductMap()
        var productList= ProductMapList()
        var locations = emptyList<LongRange>()
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
        seeds.startList.forEachIndexed {index: Int, l: Long ->
            locations+=getMappingBySeedRange(LongRange(l,l+seeds.rangeList.get(index)-1),productList)

            println("seed" + index )
        }
        var min= emptyList<Long>()
        locations.forEach{
            min+=it
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
        var startList= emptyList<Long>()
        var rangeList= emptyList<Long>()
        fun addSeeds(seedsString: String){
            var seedsLong = seedsString.split(" ").filter { !it.isEmpty() }.map { it.toLong() }
            var i = 1
            while(i<=seedsLong.size){
                if(i%2==0){
                    startList+=seedsLong.get(i-2)
                    rangeList+=seedsLong.get(i-1)
                }
                i++
            }
        }
    }
    class ProductMap {
        var sourceName: String = ""
        var destName: String = ""
        var mappings: List<Mappings> = emptyList()
        fun addNames(nameRow: String){
            sourceName= nameRow.split("-").first()
            destName= nameRow.split("-").last()
        }
        fun addMappings(mapString: String){
            var source = mapString.split(" ").get(1).toLong()
            var dest = mapString.split(" ").first().toLong()
            var range = mapString.split(" ").last().toLong()
            mappings+=Mappings(source,dest,range)
        }
        fun getMappingBySource(source: Long): Long{
            var filtermap = mappings.filter { source in it.source..it.source+it.range }
            if(filtermap.size == 0){
                return source
            }else{
                 return source-filtermap.first().source+filtermap.first().dest
            }

        }
        fun getMappingByRange(source: LongRange): List<LongRange> {
            var ranges = emptyList<LongRange>().toMutableList()
            var nums = emptyList<Long>()
            mappings.forEach{
                var t = source.intersect(LongRange(it.source, it.source + it.range))
                var m = source - t
                nums += m.toList()
                if(!t.isEmpty()){
                    var l =LongRange(it.dest-it.source+t.first(),it.dest-it.source+t.last())
                    ranges.add(l)
                    //if(!m.isEmpty() && !m.size.equals(source.last-source.first)){
                    //    var test = 0
                    //    var q =LongRange(it.dest-it.source+m.first(),it.dest-it.source+m.last())
                    //    ranges.add(q)
                    //}
                    ranges.toList()
                }
            }
            nums.forEach{
                var f =getMappingBySource(it)
                ranges.add(LongRange(f,f))
            }
            if(ranges.isEmpty()) ranges.add(source)
            return ranges


        }
    }
    class ProductMapList{
        var productMapList = emptyList<ProductMap>()
        fun getBySource(source: String): ProductMap {
            return productMapList.filter { it.sourceName==source }.first()
        }
    }
    class Mappings(
        val source: Long,
        val dest: Long,
        val range: Long
    )
    fun getMappingBySeed(seed: Long, productMapList: ProductMapList): Long{
        var name="seed"
        var currentSource = seed;
        var foundMapping = 0
        while(name!="location"){
            var productMap= productMapList.getBySource(name)
            name=productMap.destName
            currentSource = productMap.getMappingBySource(currentSource)
        }
       
       // println(currentSource)
        return currentSource
    }
    fun getMappingBySeedRange(seed: LongRange, productMapList: ProductMapList): List<LongRange>{
        var name="seed"
        var currentSource = seed;
        var foundMapping = 0
        var ranges = productMapList.getBySource(name).getMappingByRange(currentSource)
        while(name!="location"){
            var productMap= productMapList.getBySource(name)
            var t = emptyList<LongRange>()
            if(name != "seed"){
                ranges.forEach{t+=productMap.getMappingByRange(it)}
                ranges = t
            }

            name=productMap.destName
        }

        // println(currentSource)
        return ranges
    }


}