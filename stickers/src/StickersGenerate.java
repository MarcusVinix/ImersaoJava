import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickersGenerate {

	public void make(InputStream inputStream, String fileName) throws Exception {

		//leitura da imagem
		// InputStream inputStream = new FileInputStream("assets/movie.jpg");
		// InputStream inputStream = new URL("https://imersao-java-apis.s3.amazonaws.com/TopMovies_1.jpg").openStream();
		BufferedImage imagemOriginal = ImageIO.read(inputStream);

		// criar nova imagem em memória com transparencia e com tamanho novo
		int width = imagemOriginal.getWidth();
		int height = imagemOriginal.getHeight();
		int newHeight = height + 200;
		BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

		// copiar a imagem original pra novo imagem (em memoria)
		Graphics2D graphics = (Graphics2D)newImage.getGraphics();
		graphics.drawImage(imagemOriginal, 0, 0, null);

		// configurar a fonte
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 64);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(font);

		//escrever uma frase na nova imagem
		graphics.drawString("TOPZERA", width / 4, newHeight - 100);

		// escrever a nova imagem em um arquivo
		ImageIO.write(newImage, "png", new File(fileName));

	}

}
