/**************************************************
* Adnan Bouaouda Arafa
* 2023, Malaga
**************************************************/
import processing.core.{PApplet, PConstants}
import processing.core.PImage

class ochoReinas extends PApplet {
  val ancho  = 200
  val alto = 200
  val bloqueX = ancho / 8
  val bloqueY = alto / 8
  val col = List(1, 2, 3, 4, 5, 6, 7, 8)

  def esSolucion(solucion: List[Int]):Boolean = {
    def n = solucion.length
    val columnas = (1 to n).toList
    val seRepiteHorizontal  = solucion == solucion.distinct
    def proyectaDiagonales1 = solucion.zip(columnas).map(e => e._1 - e._2)
    val seRepiteDiagonales1 = proyectaDiagonales1 == proyectaDiagonales1.distinct
    def proyectaDiagonales2 = solucion.zip(columnas).map(e => e._1 + e._2)
    val seRepiteDiagonales2 = proyectaDiagonales2 == proyectaDiagonales2.distinct
    seRepiteHorizontal && seRepiteDiagonales1 && seRepiteDiagonales2
  }
  def rota(p: List[Int], n:Int=1): List[Int]={
    val ind = List(1, 2, 3, 4, 5, 6, 7, 8)
    var pR = p
    def rotar = pR.map(9-_).zip(ind).map(_.swap).sortBy(_._2).map(_._1)
    for(i<- 1 to n)
      pR = rotar
    pR
  }
  def equivalentes(p: List[Int]): List[List[Int]] ={
    def r = p.reverse
    List(rota(p), rota(p, 2), rota(p, 3),
      r, rota(r), rota(r, 2), rota(r, 3))
  }
  def filtrar (p: List[List[Int]], q: List[Int]) = {
    p.filterNot(equivalentes(q).contains(_))
  }
  def fund(p: List[List[Int]]): List[List[Int]] = p.size match{
    case 0  => List()
    case 1  => p
    case 2  => p.head :: filtrar(p.tail, p.head)
    case _  => p.head :: fund(filtrar(p.tail, p.head))
  }
  override def settings() = {
    size(875, 650)
  }
  override def draw() {
    noLoop()
    val reina : PImage = loadImage(".\\reina.png")
    val cuadradoNegro : PImage = loadImage(".\\cuadrado-negro.jpg")
    val cuadradoBlanco : PImage = loadImage(".\\cuadrado-blanco.jpg")

    reina.resize(bloqueX, bloqueY)
    cuadradoBlanco.resize(bloqueX, bloqueY)
    cuadradoNegro.resize(bloqueX, bloqueY)

    def tablero(x:Int, y:Int):Unit ={
      val t = Array.ofDim[Int](8,8)
        .map(_.zipWithIndex).zipWithIndex
        .map(e => e._1.map(a => (a._1 + e._2, a._2))).flatten
      def tB = t.filter(e => (e._1 + e._2) % 2 == 0)
      def tN = t.filter(e => (e._1 + e._2) % 2 != 0)
      tB.map(e => image(cuadradoBlanco, (e._1+x*9)*bloqueX, (e._2+y*9)*bloqueY ))
      tN.map(e => image(cuadradoNegro, (e._1+x*9)*bloqueX, (e._2+y*9)*bloqueY))
    }
    def colocaReinas (p:List[Int], x:Int=0, y:Int=0) : Unit = {
      col.zip(p.map(9-_))
        .map(e =>  image(reina, (e._1-1+x*9)*bloqueX, (e._2-1+y*9)*bloqueY))
    }
    var fundamentales = fund(col.permutations.filter(esSolucion(_)).toList)
    val xy = (0 to 3).map(e => (e, 0)) ++ (0 to 3).map(e => (e, 1)) ++
      (0 to 3).map(e => (e, 2)) ++ (0 to 3).map(e => (e, 2))
    xy.map(e => tablero(e._1,e._2))
    fundamentales.zip(xy).map(e => colocaReinas(e._1, e._2._1, e._2._2))
  }
}
object Main {
  def main(args: Array[String]): Unit = {
    PApplet.main("ochoReinas")
  }
}