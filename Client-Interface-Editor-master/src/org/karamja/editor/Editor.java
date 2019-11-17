package org.karamja.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SpinnerListModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.Checkbox;

public class Editor extends JFrame {

	static BufferedImage img = null;
	static BufferedImage selectedImg = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setTitle("RSC Interface Generator by Tyler Johnson");
					frame.setVisible(true);
					//frame.
					
				    img = ImageIO.read(new File("2823.png"));
				    
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	int boxHeight = 0;
	int boxWidth = 0;
	Point buttonCurrentPoint = null;
	Point buttonEndPoint = null;
	private ArrayList<String> buttons = new ArrayList<>();
	Point buttonStartPoint = null;
	private boolean buttonStartXActive = false;
	private JPanel contentPane;
	Point currentPoint = null;
	ArrayList<Button> displayButtons = new ArrayList<Button>();
	
	ArrayList<Image> displayImages = new ArrayList<Image>();
	private boolean drawBorder = false;
	private ArrayList<String> drawStrings = new ArrayList<>();
	private ArrayList<DrawString> displayDrawStrings = new ArrayList<>();
	Point endPoint = null;
	private JTextField height;
	private ArrayList<String> images = new ArrayList<>();
	private int mouseX = 0;
	private int mouseY = 0;
	

	Point startPoint = null;
	
	private JTextField startX;
	private boolean startXActive = false, startYActive = false, widthActive = false,
			heightActive = false, drawingStringBox = false, drawingBox = false, drawingButton = false, drawingImage = false, collectingStringX = false, drawingFrameBox = false;
	private JTextField startY;
	private JTextField width;
	int x = 0;
	int y = 0;
	private JTextField classname;
	private JTextField frameTitle;
	private JTextField frameX;
	private JTextField frameY;
	private JTextField frameWidth;
	private JTextField frameHeight;
	private JTextField boxName;
	private JTextField boxText;
	private JTextField textField;
	private JTextField stringX;
	private JTextField stringY;
	private JTextField txtxffffff;
	/**
	 * Create the frame.
	 * I guess the eclipse editor put everything in here D: Lol
	 */
	public Editor() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(2, 2));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setSize(400, 600);
		contentPane.add(panel, BorderLayout.WEST);
		
		final JTextPane textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.SOUTH);
		
		final JPanel display = new JPanel(){

			@Override
			public Color getBackground(){
				return Color.RED;
			}
			
			@Override
            public Dimension getPreferredSize() {
                return new Dimension(512, 334);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                g.setColor(Color.PINK);
                try{
                	g.drawRect(Integer.parseInt(frameX.getText()), Integer.parseInt(frameY.getText()), Integer.parseInt(frameWidth.getText()), Integer.parseInt(frameHeight.getText()));
                	g.fillRect(Integer.parseInt(frameX.getText()), Integer.parseInt(frameY.getText()), Integer.parseInt(frameWidth.getText()), Integer.parseInt(frameHeight.getText()));
                } catch(Exception e){
                	
                }
                g.setColor(Color.black);
                if (startPoint != null && currentPoint != null) {
	                g.setColor(Color.black);
	                int x = Math.min(startPoint.x, currentPoint.x);
	                int y = Math.min(startPoint.y, currentPoint.y);
	                int width = Math.abs(startPoint.x - currentPoint.x);
	                int height = Math.abs(startPoint.y - currentPoint.y);
	                
	                g.drawRect(x, y, width, height);
	                g.fillRect(x, y, width, height);
                }

                
                
                g.setColor(Color.BLUE);
                for(int i = 0; i < displayButtons.size(); i++){
                	displayButtons.get(i).draw(g);
                }
                if (buttonStartPoint != null && buttonCurrentPoint != null) {
	                int x = Math.min(buttonStartPoint.x, buttonCurrentPoint.x);
	                int y = Math.min(buttonStartPoint.y, buttonCurrentPoint.y);
	                int width = Math.abs(buttonStartPoint.x - buttonCurrentPoint.x);
	                int height = Math.abs(buttonStartPoint.y - buttonCurrentPoint.y);
	                
	                g.drawRect(x, y, width, height);
	                g.fillRect(x, y, width, height);
                }
                
                g.setColor(Color.WHITE);
                for(int i = 0; i < displayDrawStrings.size(); i++){
                	g.drawString(displayDrawStrings.get(i).getString(), displayDrawStrings.get(i).getX(), displayDrawStrings.get(i).getY());
                }
                for(int i = 0; i < displayImages.size(); i++){
                	g.drawImage(displayImages.get(i).getImage(), displayImages.get(i).getStartX(), displayImages.get(i).getStartY(), this);
                }
                
                if(selectedImg != null){
                	g.drawImage(selectedImg, mouseX, mouseY, this);
                }
                
                Graphics gfx = img.getGraphics();
                g.drawImage(img, 0, 334-4, this);
            }

		};
		contentPane.add(display, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Generate Code");
		final String fileNames[] = new String[5000];
		
		JLabel lblNewLabel_1 = new JLabel("Class Name");
		
		classname = new JTextField();
		classname.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JLabel lblFrameTitle = new JLabel("Frame Title");
		
		frameTitle = new JTextField();
		frameTitle.setColumns(10);
		GroupLayout groupPanel = new GroupLayout(panel);
		groupPanel.setHorizontalGroup(
			groupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupPanel.createSequentialGroup()
					.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(groupPanel.createSequentialGroup()
							.addGap(84)
							.addComponent(btnNewButton))
						.addGroup(groupPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblFrameTitle))
							.addGap(56)
							.addGroup(groupPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(frameTitle, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addComponent(classname, Alignment.LEADING, 0, 0, Short.MAX_VALUE)))
						.addGroup(groupPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupPanel.setVerticalGroup(
			groupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupPanel.createSequentialGroup()
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(classname, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFrameTitle)
						.addComponent(frameTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap())
		);




		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Button", null, panel_2, null);
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("CheckBox", null, panel_3, null);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Box", null, panel_1, null);

		startX = new JTextField();
		startX.setBounds(100, 22, 86, 20);
		startX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				drawingBox = true;
				startXActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				drawingBox = false;
				startXActive = false;
			}
		});
		startX.setColumns(10);

		JLabel lblNewLabel = new JLabel("Box Start X");
		lblNewLabel.setBounds(10, 25, 80, 14);

		startY = new JTextField();
		startY.setBounds(100, 47, 86, 20);
		startY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				startYActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				startYActive = false;
			}
		});
		startY.setColumns(10);

		JLabel lblBoxStartY = new JLabel("Box Start Y");
		lblBoxStartY.setBounds(10, 50, 80, 14);

		width = new JTextField();
		width.setBounds(100, 72, 86, 20);
		width.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				widthActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				widthActive = false;
			}
		});
		width.setColumns(10);

		JLabel lblBoxEndX = new JLabel("Box Width");
		lblBoxEndX.setBounds(10, 75, 80, 14);

		height = new JTextField();
		height.setBounds(100, 100, 86, 20);
		height.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				heightActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				heightActive = false;
			}
		});
		height.setColumns(10);

		JLabel lblBoxEndY = new JLabel("Box Height");
		lblBoxEndY.setBounds(10, 100, 80, 14);
		
		final Checkbox checkbox = new Checkbox("Append Action");
		checkbox.setBounds(122, 126, 95, 22);

		JButton btnAddBox = new JButton("Add Box");
		btnAddBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Button b = new Button(boxName.getText(), boxText.getText(),Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), checkbox.getState());
				startX.setText("");
				startY.setText("");
				width.setText("");
				height.setText("");
				checkbox.setState(false);
				displayButtons.add(b);
			}
		});
		btnAddBox.setBounds(122, 155, 99, 23);
		panel_1.setLayout(null);
		panel_1.add(lblNewLabel);
		panel_1.add(lblBoxEndY);
		panel_1.add(lblBoxEndX);
		panel_1.add(lblBoxStartY);
		panel_1.add(btnAddBox);
		panel_1.add(startY);
		panel_1.add(width);
		panel_1.add(height);
		panel_1.add(startX);
		panel_1.add(checkbox);
		
		JLabel lblNewLabel_6 = new JLabel("Code Name");
		lblNewLabel_6.setBounds(196, 25, 67, 14);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Box Text");
		lblNewLabel_7.setBounds(196, 50, 67, 14);
		panel_1.add(lblNewLabel_7);
		
		boxName = new JTextField();
		boxName.setBounds(270, 22, 86, 20);
		panel_1.add(boxName);
		boxName.setColumns(10);
		
		boxText = new JTextField();
		boxText.setBounds(270, 47, 86, 20);
		panel_1.add(boxText);
		boxText.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Sprite", null, panel_4, null);
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("String", null, panel_5, null);
		panel_5.setLayout(null);
		
		final JLabel stringText = new JLabel("String Text");
		stringText.setBounds(10, 11, 80, 14);
		panel_5.add(stringText);
		
		final JLabel stringXLabel = new JLabel("String X");
		stringXLabel.setBounds(10, 36, 80, 14);
		panel_5.add(stringXLabel);
		
		final JLabel stringYLabel = new JLabel("String Y");
		stringYLabel.setBounds(10, 61, 80, 14);
		panel_5.add(stringYLabel);
		
		JLabel stringColor = new JLabel("String Color");
		stringColor.setBounds(10, 86, 80, 14);
		panel_5.add(stringColor);
		
		textField = new JTextField();
		textField.setBounds(100, 8, 180, 20);
		panel_5.add(textField);
		textField.setColumns(10);
		
		stringX = new JTextField();
		stringX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				drawingStringBox = true;
				//startXActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				drawingStringBox = false;
				//startXActive = false;
			}
		});
		stringX.setBounds(100, 33, 86, 20);
		panel_5.add(stringX);
		stringX.setColumns(10);
		
		stringY = new JTextField();
		stringY.setBounds(100, 58, 86, 20);
		panel_5.add(stringY);
		stringY.setColumns(10);
		
		txtxffffff = new JTextField();
		txtxffffff.setText("0xFFFFFF");
		txtxffffff.setBounds(100, 83, 86, 20);
		panel_5.add(txtxffffff);
		txtxffffff.setColumns(10);
		
		JButton stringButton = new JButton("Add String");
		stringButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DrawString ds = new DrawString(stringText.getText(), Integer.parseInt(stringX.getText()), Integer.parseInt(stringY.getText()));
				displayDrawStrings.add(ds);
				stringText.setText("");
				stringX.setText("");
				stringY.setText("");
			}
		});
		stringButton.setBounds(140, 183, 89, 23);
		panel_5.add(stringButton);
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Line", null, panel_6, null);
		JPanel panel_7 = new JPanel();
		tabbedPane.addTab("LineY", null, panel_7, null);
		JPanel panel_8 = new JPanel();
		tabbedPane.addTab("ScrollBar", null, panel_8, null);
		JPanel panel_9 = new JPanel();
		tabbedPane.addTab("TextBox", null, panel_9, null);
		JPanel panel_10 = new JPanel();
		tabbedPane.addTab("Frame", null, panel_9, null);
		panel_9.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Frame Start X");
		lblNewLabel_2.setBounds(10, 11, 100, 14);
		panel_9.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Frame Start Y");
		lblNewLabel_3.setBounds(10, 36, 100, 14);
		panel_9.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Frame Width");
		lblNewLabel_4.setBounds(10, 61, 100, 14);
		panel_9.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Frame Height");
		lblNewLabel_5.setBounds(10, 86, 100, 14);
		panel_9.add(lblNewLabel_5);
		
		frameX = new JTextField();
		frameX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				drawingFrameBox = true;
				//startXActive = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				drawingFrameBox = false;
				//startXActive = false;
			}
		});
		frameX.setBounds(140, 8, 86, 20);
		panel_9.add(frameX);
		frameX.setColumns(10);
		
		frameY = new JTextField();
		frameY.setBounds(140, 36, 86, 20);
		panel_9.add(frameY);
		frameY.setColumns(10);
		
		frameWidth = new JTextField();
		frameWidth.setBounds(140, 61, 86, 20);
		panel_9.add(frameWidth);
		frameWidth.setColumns(10);
		
		frameHeight = new JTextField();
		frameHeight.setBounds(140, 86, 86, 20);
		panel_9.add(frameHeight);
		frameHeight.setColumns(10);
		panel.setLayout(groupPanel);


		Rectangle box;
		display.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(drawingFrameBox)
					currentPoint = e.getPoint();
				if(drawingBox)
					currentPoint = e.getPoint();
				if(drawingButton)
					buttonCurrentPoint = e.getPoint();
				if(drawingStringBox)
					currentPoint = e.getPoint();
				if(drawingImage){
					mouseX = e.getX();
					mouseY = e.getY();
				}
				//if(drawingBox){
				display.repaint();
				//}
			}
		});
		display.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(buttonStartXActive) {
					//buttonStartX.setText(Integer.toString(e.getX()));
				} else if(startXActive){
					startX.setText(Integer.toString(e.getX()));
				} else if(startYActive){
					startY.setText(Integer.toString(e.getY()));
				} else if(widthActive){

				} else if(heightActive){

				} else if(collectingStringX){
					//textField_1.setText(Integer.toString(e.getX()));
					//textField_2.setText(Integer.toString(e.getY()));
				}
				//if(spinner.getValue() != null){
				//	display.repaint();
				//}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(drawingFrameBox){
					startPoint = e.getPoint();
					frameX.setText(Integer.toString(startPoint.x));
					frameY.setText(Integer.toString(startPoint.y));
				}
				if(drawingStringBox){
					startPoint = e.getPoint();
					stringX.setText(Integer.toString(startPoint.x));
					stringY.setText(Integer.toString(startPoint.y));
				}
				if(drawingBox){
					startPoint = e.getPoint();
					startX.setText(Integer.toString(startPoint.x));
					startY.setText(Integer.toString(startPoint.y));
				} else if (drawingButton){
					buttonStartPoint = e.getPoint();
					//buttonStartX.setText(Integer.toString(buttonStartPoint.x));
					//buttonStartY.setText(Integer.toString(buttonStartPoint.y));
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				endPoint = e.getPoint();
				buttonEndPoint = e.getPoint();
				//if(drawingStringBox){
				//	stringX.setText(Integer.toString(Math.abs(startPoint.x - currentPoint.x)));
				//	.setText(Integer.toString(Math.abs(startPoint.y - currentPoint.y)));
				//}
				if(drawingFrameBox){
					frameWidth.setText(Integer.toString(Math.abs(startPoint.x - currentPoint.x)));
					frameHeight.setText(Integer.toString(Math.abs(startPoint.y - currentPoint.y)));
				}
				if(drawingBox){
					width.setText(Integer.toString(Math.abs(startPoint.x - currentPoint.x)));
					height.setText(Integer.toString(Math.abs(startPoint.y - currentPoint.y)));
					//currentPoint = null;
				} else if (drawingButton){
					//buttonEndX.setText(Integer.toString(buttonCurrentPoint.x));
					//buttonEndY.setText(Integer.toString(buttonCurrentPoint.y));
				}
				display.repaint();
			}
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer generated = new StringBuffer();
				
				
				generated.append("import org.rscemulation.client.mudclient;\n");
				generated.append("import org.rscemulation.client.gfx.components.*;\n");
				generated.append("import org.rscemulation.client.gfx.action.*;\n");
				generated.append("import org.rscemulation.client.gfx.*;\n\n");
				
				generated.append("public class " + classname.getText() +  " extends GraphicalOverlay {\n\n");
				
				for(int i = 0; i < displayButtons.size(); i++){
					generated.append("Box " + displayButtons.get(i).name + ";\n");
				}
				
				generated.append("\nprivate mudclient<?> mc;");
				generated.append("\n\npublic " + classname.getText() + "(mudclient<?> mc) {\n");
				generated.append("super(mc);\n");
				generated.append("setMenu(true);\n");
				generated.append("this.mc = mc;\n");

				String a = "GameFrame frame = new GameFrame(\"" + frameTitle.getText() + "\", new Rectangle(" + frameX.getText()+","+frameY.getText()+ ","+ frameWidth.getText() + "," + frameHeight.getText()+ "));";
				generated.append(a);
				
				for(int i = 0; i < displayButtons.size(); i++){
					generated.append(displayButtons.get(i).toString());
				}
				
				for(int i = 0; i < displayDrawStrings.size(); i++){
					generated.append(displayDrawStrings.get(i).toString());
				}
				
				generated.append("\n } \n\n }");
				
				
				for(int i = 0; i < images.size(); i++){
					generated.append(images.get(i));
				}
				
				String g = generated.toString();
				textPane.setText(g);

			}
		});

	}
}
