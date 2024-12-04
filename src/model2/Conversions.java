/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.util.HashMap;

/**
 *
 * @author Mostafa
 */
public class Conversions {
    private HashMap<String, String> ConvertTable = new HashMap<String, String>();
    public HashMap<String, String> getConvertTable() {
        return ConvertTable;
    }
    public void setConvertTable(HashMap<String, String> ConvertTable) {
        this.ConvertTable = ConvertTable;
    }
     public HashMap Initialization(HashMap<String, String> ConvertTable)
     {
              ConvertTable.put("0", "0000");
              ConvertTable.put("1", "0001");
              ConvertTable.put("2", "0010");
              ConvertTable.put("3", "0011");
              ConvertTable.put("4", "0100");
              ConvertTable.put("5", "0101");
              ConvertTable.put("6", "0110");
              ConvertTable.put("7", "0111");
              ConvertTable.put("8", "1000");
              ConvertTable.put("9", "1001");
              ConvertTable.put("A", "1010");
              ConvertTable.put("B", "1011");
              ConvertTable.put("C", "1100");
              ConvertTable.put("D", "1101");
              ConvertTable.put("E", "1110");
              ConvertTable.put("F", "1111");
              return ConvertTable;
          }
     public String HexToBinary(String Number,Conversions convert)
     {
         String Binary="";
         if (Number.length()==1)
         {
             
              Binary=ConvertTable.get(Number);
              return Binary;
         }
         else 
         {
             Binary=ConvertTable.get(""+Number.charAt(0));
             Binary+=ConvertTable.get(""+Number.charAt(1));
              return Binary;
         }
      }
     public  int ConvertBinaryToDecimal(String binarybyte)
     {
                int dec=Integer.parseInt(binarybyte, 2);
                return dec;
                }
   public String intToBinary(int n)
    {
        String s = "";
        String Zeros="";
        while (n > 0)
        {
            s =  ( (n % 2 ) == 0 ? "0" : "1") +s;
            n = n / 2;
        }
        for (int i=0;i<8-s.length();i++)
        {
            Zeros+="0";
        }
        return Zeros+s;
    } 
   public String SelectSpecificBits(String ByteBinary,String ByteInDecimal)
   {
       String SpecificBits="";
       for (int i=0;i<ByteInDecimal.length();i++)
       {
           if (ByteInDecimal.charAt(i)=='1')
           {
               SpecificBits+=ByteBinary.charAt(i);
           }
       }
       return SpecificBits;
   }
   
   public String TwoSComplement(String data)
   {
       String temp="";
       if(data.charAt(0)=='0'){
           return data;
       }else{
       boolean flag=true;
       for (int i=data.length()-1;i>=0;i--)
       {
           if(data.charAt(i)!='1' && flag==true){temp=data.charAt(i)+temp;}
           else if (data.charAt(i)=='1' && flag==true){flag=false;temp=data.charAt(i)+temp;}
           else if (flag==false)
           {
               if (data.charAt(i)=='1'){temp='0'+temp;}
               else{temp='1'+temp;}
           }
       }
       return temp;
       }
   }
}
