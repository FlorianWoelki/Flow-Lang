package com.florianwoelki.flow.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public class IDE extends JFrame {

    private final Console console;
    private final JTextPane text;

    private final Preferences prefs;

    public IDE() {
        super( "Flow - IDE" );

        text = new JTextPane();
        text.setFont( new Font( Font.SANS_SERIF, Font.PLAIN, 16 ) );

        JScrollPane scroll = new JScrollPane( text );
        scroll.setBorder( null );
        scroll.getVerticalScrollBar().setPreferredSize( new Dimension( 0, 0 ) );

        console = new Console();

        prefs = new Preferences( this );

        JScrollPane consoleScroll = new JScrollPane( console );
        consoleScroll.setBorder( null );
        consoleScroll.getVerticalScrollBar().setPreferredSize( new Dimension( 0, 0 ) );

        JSplitPane split = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, scroll, consoleScroll );
        split.setOneTouchExpandable( true );
        split.setDividerLocation( 320 );

        add( split );

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu( "File" );
        JMenuItem run = new JMenuItem( "Run" ), save = new JMenuItem( "Save" ), load = new JMenuItem( "Load" ), preferences = new JMenuItem( "Preferences" );

        menuBar.add( menu );

        menu.add( run );
        menu.add( save );
        menu.add( load );
        menu.addSeparator();
        menu.add( preferences );

        setJMenuBar( menuBar );

        int meta = System.getProperty( "os.name" ).startsWith( "Mac" ) ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK;

        run.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, meta ) );
        run.addActionListener( (e) -> console.run( new com.florianwoelki.flow.lang.Class( text.getText().split( "\n" ) ) ) );

        save.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, meta ) );
        save.addActionListener( (event) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter( new FileNameExtensionFilter( "Flow Code", "flow" ) );
            chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
            chooser.setMultiSelectionEnabled( false );

            if ( chooser.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ) {
                try {
                    BufferedWriter writer = new BufferedWriter( new FileWriter( new File( chooser.getSelectedFile().getAbsolutePath() + ".flow" ) ) );

                    for ( String line : text.getText().split( "\n" ) ) {
                        writer.write( line + "\n" );
                    }

                    writer.close();
                } catch ( IOException e ) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(), e );
                }
            }
        } );

        load.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_L, meta ) );
        load.addActionListener( (event) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter( new FileNameExtensionFilter( "Flow Code", "flow" ) );
            chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
            chooser.setMultiSelectionEnabled( false );

            if ( chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
                text.setText( "" );

                try {
                    BufferedReader reader = new BufferedReader( new FileReader( chooser.getSelectedFile() ) );

                    while ( reader.ready() ) {
                        text.getDocument().insertString( text.getDocument().getLength(), reader.readLine() + "\n", null );
                    }

                    reader.close();
                } catch ( Exception e ) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(), e );
                }
            }
        } );

        preferences.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_COMMA, meta ) );
        preferences.setEnabled( false );
        preferences.addActionListener( (e) ->
        {
            JOptionPane.showMessageDialog( IDE.this, prefs, "Preferences", JOptionPane.PLAIN_MESSAGE );
        } );

        setSize( 640, 480 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        setVisible( true );
    }

    public void setIDEFont(Font font) {
        text.setFont( font );
    }

}
