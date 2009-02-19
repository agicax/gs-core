/*
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package org.miv.graphstream.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.miv.util.NotFoundException;

/**
 * Reader for the "edge" graph format.
 *
 * <p>
 * The edge graph format is a very simple and lightweight format where
 * each line describe an edge by giving two node names. The nodes are
 * created implicitly.
 * </p>
 * 
 * <p>
 * This reader also understand the derivative format where a line contains
 * a first node name, followed by several node names separated by spaces.
 * In this case it links the first node with all other node name following.
 * </p>
 *
 * @author Antoine Dutot
 * @author Yoann Pign�
 * @since 2007
 */
public class GraphReaderEdge extends GraphReaderBase
{
// Attribute
	
	/**
	 * Allocator for edge identifiers.
	 */
	protected int edgeid = 0;
	
	/**
	 * By default, consider edges as undirected.
	 */
	protected boolean directed = false;
	
// Construction

	/**
	 * New reader for the "edge" format.
	 */
	public GraphReaderEdge()
	{
		this( false );
	}
	
	/**
	 * New reader for the "edge" format.
	 * @param edgesAreDirected If true edges are considered directed.
	 */
	public GraphReaderEdge( boolean edgesAreDirected )
	{
		directed = edgesAreDirected;
	}
	
// Commands
	
	@Override
	protected void continueParsingInInclude() throws IOException, GraphParseException
	{
		// Should not happen, EDGE files cannot be nested.
	}

	@Override
	public boolean nextEvents() throws GraphParseException, IOException
	{
		String id1 = getWordOrNumberOrStringOrEolOrEof();
		
		if( id1.equals( "EOL" ) )
		{
			// Empty line.
		}
		else if( id1.equals( "EOF" ) )
		{
			return false;
		}
		else
		{
			String id2 = getWordOrNumberOrStringOrEolOrEof();
			
			while( ! id2.equals( "EOL" ) )
			{
				if( ! id1.equals( id2 ) )
				{
					for( GraphReaderListener listener: listeners )
						listener.edgeAdded( Integer.toString( edgeid++ ),
							id1, id2, directed, null );				
					for( GraphReaderListenerExtended listener: listeners2 )
						listener.edgeAdded( Integer.toString( edgeid++ ),
							id1, id2, directed, null );
				}
				
				id2 = getWordOrNumberOrStringOrEolOrEof();
			}
		}
		
		return true;
	}
	
	@Override
	public void begin( String filename ) throws NotFoundException, GraphParseException, IOException
	{
		super.begin( filename );
		init();
	}
	
	protected void init() throws GraphParseException, IOException
	{
		st.eolIsSignificant( true );
		st.commentChar( '#' );
	}

	@Override
	public void begin( InputStream stream ) throws GraphParseException, IOException
	{
		super.begin( stream );
		init();
	}

	@Override
	public void begin( Reader reader ) throws GraphParseException, IOException
	{
		super.begin( reader );
		init();
	}

	public boolean nextStep() throws GraphParseException, IOException
	{
		return nextEvents();
	}

	@Override
	public void end() throws GraphParseException, IOException
	{
		super.end();
	}

	@Override
	protected void parseGraph() throws GraphParseException, IOException
	{
		boolean loop = true;
		
		while( loop )
		{
			loop = nextEvents();
		}
		
		end();
	}
}