object HelloWorld {
//  def main(args: Array[String]) {
//    if (args.length > 1) {
//      println("Hello, world! " + args(0))
//    }
//  }
  def main(args:Array[String]) = {  
    var greeting = "" 
    args.foreach(arg => greeting += (arg + " "))  
    if (args.length > 0) greeting = greeting.substring(0, greeting.length - 1)  
   
    println(greeting)  
  }  
}
