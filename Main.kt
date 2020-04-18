package machine

import java.util.Scanner

fun main() {
    //val scanner = Scanner(System.`in`)
    val machine = Machine()
    //val espresso = CupOfEspresso()
    machine.GivingOrder()


}

open class CupOfCoffee (var NormWater: Int = 200, var NormBeans: Int = 15, var NormMilk: Int = 50)

class CupOfEspresso (NormWater: Int = 250, NormBeans: Int = 16, var Price: Int = 4): CupOfCoffee(NormWater, NormBeans)

class CupOfLatte (NormWater: Int = 350, NormBeans: Int = 20, NormMilk: Int = 75, var Price: Int = 7): CupOfCoffee(NormWater, NormBeans, NormMilk)

class CupOfCappuccino (NormWater: Int = 200, NormBeans: Int = 12, NormMilk: Int = 100, var Price: Int = 6): CupOfCoffee(NormWater, NormBeans, NormMilk)

class Machine (var Water: Int = 400, var Beans: Int = 120, var Milk: Int = 540, var Money: Int = 550, var DisposableCups: Int = 9) {
    var realStatus = Status.CHOOSINGACTION

    fun GivingOrder () {
        val scanner = Scanner(System.`in`)
        val espresso = CupOfEspresso()
        loop@ do {
            print("Write action (buy, fill, take, remaining, exit): > ")
            var command = scanner.next()
            println()
            when (command) {
                "buy" -> {
                    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > ")
                    //val buyng = scanner.nextInt()
                    when (scanner.next()) {
                        "1" -> {
                            val cup = CupOfEspresso()
                            if (MaxCup(cup, this) >= 1) {
                                this.Water -= cup.NormWater
                                this.Beans -= cup.NormBeans
                                this.DisposableCups -= 1
                                this.Money += cup.Price
                                println("I have enough resources, making you a coffee!")
                            } else {
                                println("Sorry, not enough ${LuckOfIngridients(cup, this)}!")
                            }
                            println()
                        }
                        "2" -> {
                            val cup = CupOfLatte()
                            MaxCup(cup, this)
                            if (MaxCup(cup, this) >= 1) {
                                this.Water -= cup.NormWater
                                this.Milk -= cup.NormMilk
                                this.Beans -= cup.NormBeans
                                this.DisposableCups -= 1
                                this.Money += cup.Price
                                println("I have enough resources, making you a coffee!")
                            } else {
                                println("Sorry, not enough ${LuckOfIngridients(cup, this)}!")
                            }
                            println()
                        }
                        "3" -> {
                            val cup = CupOfCappuccino()
                            MaxCup(cup, this)
                            if (MaxCup(cup, this) >= 1) {
                                this.Water -= cup.NormWater
                                this.Milk -= cup.NormMilk
                                this.Beans -= cup.NormBeans
                                this.DisposableCups -= 1
                                this.Money += cup.Price
                                println("I have enough resources, making you a coffee!")
                            } else {
                                println("Sorry, not enough ${LuckOfIngridients(cup, this)}!")
                            }
                            println()
                        }
                        "back" -> continue@loop
                    }
                }
                "fill" -> {
                    print("Write how many ml of water do you want to add: > ")
                    val water = scanner.nextInt()
                    print("Write how many ml of milk do you want to add: > ")
                    val milk = scanner.nextInt()
                    print("Write how many grams of coffee beans do you want to add: > ")
                    val beans = scanner.nextInt()
                    print("Write how many disposable cups of coffee do you want to add: > ")
                    val cups = scanner.nextInt()
                    this.Water += water
                    this.Milk += milk
                    this.Beans += beans
                    this.DisposableCups += cups
                    println()
                }
                "take" -> {
                    println("I gave you \$${this.Money}")
                    println()
                    /*println("${machine.Water} of water")
                    println("${machine.Milk} of milk")
                    println("${machine.Beans} of coffee beans")
                    println("${machine.DisposableCups} of disposable cups")
                    println("${machine.Money} of money")
                    println() */
                    this.Money = 0
                }
                "remaining" -> {
                    println("The coffee machine has:")
                    println("${this.Water} of water")
                    println("${this.Milk} of milk")
                    println("${this.Beans} of coffee beans")
                    println("${this.DisposableCups} of disposable cups")
                    if (this.Money > 0) println("$${this.Money} of money")
                    else println("0 of money")
                    println()
                }

            }
        } while (command != "exit")
    }

}

fun MaxCup (cup: CupOfCoffee, machine: Machine): Int {
    return minOf(machine.Water/cup.NormWater, machine.Beans/cup.NormBeans, machine.Milk/cup.NormMilk)
}

fun LuckOfIngridients(cup: CupOfCoffee, machine: Machine): String {
    val Ingridients = mutableMapOf("water" to machine.Water/cup.NormWater, "beans" to machine.Beans/cup.NormBeans, "milk" to machine.Milk/cup.NormMilk)
    return Ingridients.filterValues { it < 1 }.keys.toString().dropLast(1).drop(1)
}

enum class Status {
    CHOOSINGACTION,
    CHOOSINGCOFE,
    FILLINGMACHINE

}
