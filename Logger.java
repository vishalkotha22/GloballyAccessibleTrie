package test;

import java.io.*;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;
import java.util.BitSet;
import java.util.Vector;
import java.util.Calendar;


public class Logger {
	
	private static boolean bDebug_ = true;
	private static boolean bInfo_ = true;
	private static boolean bError_ = true;
	private static boolean bFatal_ = true;
	private static RandomAccessFile pageErroFile_ = null;

	public static final void log( Class c, String strMessage ) {
		if( bInfo_ ) {
			String strClassName = "";
			if( c != null) {
				strClassName = c.getName() +" : ";	
			}

			System.out.println( new Date().toString() + 
					" : I : " + strClassName + strMessage);
		}
	}

	public static final void log( String strClassName, String strMessage ) {
		if( bInfo_ ) {
			System.out.println( new Date().toString() + 
					" : I : " + strClassName + strMessage);
		}
	}

	public static final void debug( Class c, String strMessage ) {
		if( bDebug_ ) {
			String strClassName = "";
			if( c != null) {
				strClassName = c.getName() +" : ";	
			}

			System.out.println( new Date().toString() + 
				" : D : " + strClassName + strMessage);
		}
	}

	public static final void debug( String strClassName, String strMessage ) {
		if( bDebug_ ) {
			System.out.println( new Date().toString() +  
					" : D : " + strClassName + strMessage);
		}
	}

	public static final void err( String strClassName, String strMessage ) {
		if( bError_ ) {
			System.err.println( new Date().toString() +  
							" : E : " + strClassName + strMessage);
		}
	}

	public static final void err( Throwable t, Class c, String strMessage ) {
		if( bError_ ) {
			String strClassName = "";
			if( c != null) {
				strClassName = c.getName() +" : ";	
			}
			System.err.println( new Date().toString() +  
					" : E : " + strClassName + strMessage);
			if( t != null ) {
				t.printStackTrace();
			}
		}
	}

	public static final void err( Throwable t, String strClassName,
										String strMessage ) {
		if( bError_ ) {
			System.err.println( new Date().toString() +  
							" : E : " + strClassName + strMessage);
			if( t != null ) {
				t.printStackTrace();
			}
		}
	}


	public static final void fatal( Class c, String strMessage ) {
		if( bFatal_ ) {
			String strClassName = "";
			if( c != null) {
				strClassName = c.getName() +" : ";	
			}

			System.err.println( new Date().toString() + 
						" : F : " + strClassName + strMessage);
		}
	}

	public static final void fatal( String strClassName, String strMessage ) {
		if( bFatal_ ) {
			System.err.println( new Date().toString() +  
							" : F : " + strClassName + strMessage);
		}
	}

	public static synchronized void logPageErrorData( String strFileName,
													  String strMessage ) {
		if( strFileName == null || strFileName.trim().length() == 0 ) {
			fatal( "Logger", " : logPageErrorData : Invalid File Name '"+ 
				strFileName +"'. Please verify and rerun the program.");
			return;
		}
		try {
			if( pageErroFile_ == null ) {
				pageErroFile_ = new RandomAccessFile( strFileName, "rw");	
			}
			
			long lLength = pageErroFile_.length();
			if( lLength > 0 ) {
				pageErroFile_.seek( lLength );
			}
			if( strMessage != null ) {
				pageErroFile_.writeBytes( strMessage );
				pageErroFile_.writeBytes( "\r\n" );
				//pageErroFile_.flush();
			}
		}
		catch(Exception e ) {
			Logger.err(e, "Logger", " : logPageErrorData : Exception : Failed "+
				"while writing page error data to the '"+ strFileName +
				"' file. Because ::: "+ e );			
		}
	}

	public static synchronized void closeLogPageErrorDataFile() {
		
		try {
			if( pageErroFile_ != null ) {
				pageErroFile_.close();
				pageErroFile_ = null;
			}
		}
		catch(Exception e ) {
			Logger.err(e, "Logger", " : closeLogPageErrorDataFile : "+
				"Exception : Failed while closing page error data to "+
				"the file. Because ::: "+ e );			
		}
	}
}
