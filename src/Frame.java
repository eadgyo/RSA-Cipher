import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.DatatypeConverter;

public class Frame extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel publicKeyLab, privateKeyLab, publicKeyLab2;
	private JButton generate, read, convert;
	private JTextField publicKeyField, privateKeyField, publicKeyExp, publicKeyExp2, publicKeyField2;
	private JTextArea text1, text2;
	private SpringLayout layout;
	private Container container;
	private RSAPublicKeySpec publicKey, publicKey2;
	private RSAPrivateKeySpec privateKey;
	private KeyPair kp;
	private KeyPairGenerator kpg;
	private KeyFactory factory;
	private Cipher cipher;
	
	public final int width = 675;
	public final int height = 425;
	public Frame()
	{
		super();
		this.setTitle("RSA");
		this.setResizable(false);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		publicKeyLab = new JLabel("Public Key");
		publicKeyLab2 = new JLabel("Public 2");
		privateKeyLab = new JLabel("Private Key");
		
		generate = new JButton("Generate");
		read = new JButton("Read");
		convert = new JButton("Convert");
		
		publicKeyField = new JTextField("", 44);
		publicKeyExp = new JTextField("", 5);
		publicKeyField2 = new JTextField("", 44);
		publicKeyExp2 = new JTextField("", 5);
		
		privateKeyField = new JTextField("", 50);
		
		text1 = new JTextArea(6, 58);
		text2 = new JTextArea(6, 58);
		text1.setLineWrap(true);
		text2.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(text1);
		JScrollPane scrollPane2 = new JScrollPane(text2);
		
		//label
		layout.putConstraint(SpringLayout.WEST, publicKeyLab, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, publicKeyLab, 10, SpringLayout.NORTH, this);
		this.add(publicKeyLab);
		
		layout.putConstraint(SpringLayout.WEST, privateKeyLab, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, privateKeyLab, 10, SpringLayout.SOUTH, publicKeyLab);
		this.add(privateKeyLab);
		
		//textfield
		layout.putConstraint(SpringLayout.WEST, publicKeyField, 10, SpringLayout.EAST, privateKeyLab);
		layout.putConstraint(SpringLayout.NORTH, publicKeyField, 10, SpringLayout.NORTH, this);
		this.add(publicKeyField);
		
		layout.putConstraint(SpringLayout.WEST, publicKeyExp, 5, SpringLayout.EAST, publicKeyField);
		layout.putConstraint(SpringLayout.NORTH, publicKeyExp, 10, SpringLayout.NORTH, this);
		this.add(publicKeyExp);
		
		layout.putConstraint(SpringLayout.WEST, privateKeyField, 10, SpringLayout.EAST, privateKeyLab);
		layout.putConstraint(SpringLayout.NORTH, privateKeyField, 5, SpringLayout.SOUTH, publicKeyField);
		this.add(privateKeyField);
		
		//Button
		layout.putConstraint(SpringLayout.WEST, generate, 10, SpringLayout.EAST, privateKeyLab);
		layout.putConstraint(SpringLayout.NORTH, generate, 5, SpringLayout.SOUTH, privateKeyLab);
		this.add(generate);
	
		//text
		layout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.SOUTH, generate);
		this.add(scrollPane);
		
		//Buttons
		layout.putConstraint(SpringLayout.WEST, read, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, read, 10, SpringLayout.SOUTH, scrollPane);
		this.add(read);
		
		layout.putConstraint(SpringLayout.WEST, convert, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, convert, 10, SpringLayout.SOUTH, read);
		this.add(convert);
		
		//label
		layout.putConstraint(SpringLayout.WEST, publicKeyLab2, 10, SpringLayout.EAST, convert);
		layout.putConstraint(SpringLayout.NORTH, publicKeyLab2, 30, SpringLayout.SOUTH, scrollPane);
		this.add(publicKeyLab2);
		
		//textfield
		layout.putConstraint(SpringLayout.WEST, publicKeyField2, 5, SpringLayout.EAST, convert);
		layout.putConstraint(SpringLayout.NORTH, publicKeyField2, 13, SpringLayout.SOUTH, read);
		this.add(publicKeyField2);
		
		layout.putConstraint(SpringLayout.WEST, publicKeyExp2, 5, SpringLayout.EAST, publicKeyField2);
		layout.putConstraint(SpringLayout.NORTH, publicKeyExp2, 13, SpringLayout.SOUTH, read);
		this.add(publicKeyExp2);
		
		//textarea
		layout.putConstraint(SpringLayout.WEST, scrollPane2, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane2, 10, SpringLayout.SOUTH, convert);
		this.add(scrollPane2);
		
		generate.addActionListener(this);
		convert.addActionListener(this);
		read.addActionListener(this);
		
		try
		{
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);			
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		try
		{
			cipher = cipher.getInstance("RSA");
		} 
		catch (NoSuchAlgorithmException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (NoSuchPaddingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			factory = KeyFactory.getInstance("RSA");
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if(source == generate)
		{
			kp = kpg.generateKeyPair();
			try
			{
				publicKey = factory.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
				privateKey = factory.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
			} 
			catch (InvalidKeySpecException e1)
			{
				e1.printStackTrace();
			}
			publicKeyField.setText(publicKey.getModulus().toString());
			publicKeyExp.setText(publicKey.getPublicExponent().toString());
			privateKeyField.setText(privateKey.getPrivateExponent().toString());
		}
		else if(source == read)
		{
			if(privateKeyField.getText() != null && privateKeyField.getText() != "" && publicKeyExp.getText() != "" && publicKeyExp.getText() != null)
			{
				BigInteger privateExp = new BigInteger(privateKeyField.getText());
				BigInteger publicMod = new BigInteger(publicKeyField.getText());
				privateKey = new RSAPrivateKeySpec(publicMod, privateExp);
				
				try
				{
					RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(privateKey.getModulus(), privateKey.getPrivateExponent());
					PrivateKey priKey = factory.generatePrivate(keySpec);
					cipher.init(Cipher.DECRYPT_MODE, priKey);
					byte[] toConvert = DatatypeConverter.parseBase64Binary(text1.getText());
					byte[] result = cipher.doFinal(toConvert);
					text2.setText(byteToString(result));
				} 
				catch (InvalidKeyException e1)
				{
					e1.printStackTrace();
				} 
				catch (IllegalBlockSizeException e1)
				{
					e1.printStackTrace();
				} 
				catch (BadPaddingException e1)
				{
					e1.printStackTrace();
				} 
				catch (InvalidKeySpecException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		else if(source == convert)
		{
			if(publicKeyField2.getText() != null && publicKeyField2.getText() != "" && publicKeyExp2.getText() != "" && publicKeyExp2.getText() != null)
			{
				BigInteger publicMod2 = new BigInteger(publicKeyField2.getText());
				BigInteger publicExp2 = new BigInteger(publicKeyExp2.getText());
				publicKey2 = new RSAPublicKeySpec(publicMod2, publicExp2);
			
				try
				{
					RSAPublicKeySpec keySpec = new RSAPublicKeySpec(publicKey2.getModulus(), publicKey2.getPublicExponent());
					PublicKey pubKey = factory.generatePublic(keySpec);
					cipher.init(Cipher.ENCRYPT_MODE, pubKey);
					byte[] toConvert = text1.getText().getBytes();
					byte[] result = cipher.doFinal(toConvert);
					text2.setText(DatatypeConverter.printBase64Binary(result));
				} 
				catch (InvalidKeyException e1)
				{
					e1.printStackTrace();
				} 
				catch (IllegalBlockSizeException e1)
				{
					e1.printStackTrace();
				} 
				catch (BadPaddingException e1)
				{
					e1.printStackTrace();
				} 
				catch (InvalidKeySpecException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
	public String byteToString(byte[] b)
	{
		try
		{
			return new String(b, "UTF8");
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
