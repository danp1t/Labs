public class First_lab
{
  public static void main(String[] args)
  {
    //создать одномерный массив "c" типа short
    //Заполнить его числами от 6 до 15 включительно в порядке возростания
    //Посчитаем длину массива
    int start = 6;
    int finish = 15;
    int lenght = finish - start + 1;
    
    //создаем массив "c"
    short[] c = new short[lenght];
    
    //Заполняем массив числами от [6; 15]
    for (int i = 0; i < lenght; i++)
    {
      c[i] = (short) start;
      start++;
    } 
    
    //Создать одномерный массив "x" типа double
    //Заполнить его 19-ю случайными числами [-14.0; 13.0)
    
    //Создаем массив "x"
    lenght = 19;
    double[] x = new double[lenght];
    
    //Заполняем рандомными числами
    for (int i = 0; i < lenght; i++)
    {
      x[i] = (int) (Math.random() * 27) - 14;
    }
    
    //Создать двумерный массив "y" 10x19.
    int hight = 10;
    int weight = 19;
    double[][] y = new double[hight][weight];
    
    for (int i = 0; i < hight; i++) 
    {
      for (int j = 0; j < weight; j++)
      {
        if (c[i] == 12) 
        {
          y[i][j] = Math.cos(Math.sin(Math.pow((x[j] * (x[j] + 4)), 2)));
        }
        else if (c[i] == 9 || c[i] == 11 || c[i] == 13 || c[i] == 14 || c[i] == 15)
        {
          y[i][j] = Math.log(Math.pow((3 * (Math.pow(Math.E, x[j]) + 1)), 2));
        }
        else
        {
         y[i][j] = Math.asin(1.0 / (Math.pow(Math.E, Math.pow( (Math.sqrt(Math.pow(Math.sin(x[j]), 2)) * (Math.pow(Math.E, (4.0 / Math.pow(x[j], x[j] + 0.25))) + 1)), Math.cbrt(Math.pow(x[j] / 2.0, 2)) )) ));
        }
      }
    }
    //Вывод
    for (int i = 0; i < hight; i++)
    {
      for (int j = 0; j < weight; j++)
      {
        System.out.printf("%-8.5f ", y[i][j]);
      }
    System.out.print("\n");
    }
  }
}
