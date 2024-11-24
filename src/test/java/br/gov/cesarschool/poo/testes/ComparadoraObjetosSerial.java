package br.gov.cesarschool.poo.testes;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//this is what we'll be using to compare test answers
//Serializar um objeto significa transformá-lo em uma sequência de bytes para que ele possa
// ser armazenado
// (em um arquivo, por exemplo) ou transmitido (pela rede, por exemplo).

//Quando dois objetos são serializados, seus estados internos são convertidos em bytes.
// Comparar os arrays de bytes resultantes é uma maneira de verificar se dois objetos têm
// exatamente os mesmos atributos com os mesmos valores.
public class ComparadoraObjetosSerial {
	public static boolean compareObjectsSerial(Serializable s1, Serializable s2) {
		ByteArrayOutputStream  bos1 = null;
		ByteArrayOutputStream  bos2 = null;
		ObjectOutputStream oos1 = null;
		ObjectOutputStream oos2 = null;
		boolean ret = true;
		try {
			bos1 = new ByteArrayOutputStream();
			bos2 = new ByteArrayOutputStream();
			oos1 = new ObjectOutputStream(bos1);
			oos2 = new ObjectOutputStream(bos2);
			oos1.writeObject(s1);
			oos2.writeObject(s2);
			byte[] b1 = bos1.toByteArray();
			byte[] b2 = bos2.toByteArray();			
			for (int i=0; i<b1.length; i++) {
				if (b2[i] != b1[i]) {
					ret = false;
					break;
				}
				i++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ret; 
	}

}
