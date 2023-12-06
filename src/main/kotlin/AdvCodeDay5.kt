

class AdvCodeDay5 {
    var utils = Utils()

    public fun del1(){
        var seeds= SeedsThree()
        var firstLine=true
        var productMap= ProductMap()
        var productList= ProductMapList()
        var locations = emptyList<Long>()
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
            (l..l+seeds.rangeList.get(index)).forEach{
                locations+=getMappingBySeed(it,productList)
            }
        }
        
        println(locations.minOrNull())
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


}