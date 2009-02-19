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

import java.util.*;

/**
 * Dynamic graph reader listener.
 *
 * <p>This interface has been deprecated by the {@link GraphReaderListenerExtended},
 * which allows to the ability to add or remove individual attributes. This
 * interface only allows to add attributes, but has no clear way to remove
 * them.</p>
 *
 * <p>A graph can be either static or dynamic. A static graph does not change
 * over time, whereas a dynamic one can vary. Each method of this interface
 * represents a variation in a graph. Such events are the apparition,
 * removal or change of a node or edge.</p>
 * 
 * <p>As dynamic graphs vary over time, a notion of time is introduced in this
 * interface by the {@link #stepBegins(double)} method. This allows to split the
 * graph description in discrete time steps. The argument of this method is the
 * current time. Each event received after a step event can be considered
 * occurring at this time.</p>
 * 
 * <p>Notice that a static and a dynamic graph can be described using this same
 * interface without changes. Indeed, one can listen at each event and directly
 * reflect changes in the graph (dynamic way), or listen at all events, and only
 * when no more events are available, reflect the changes (static way).</p> 
 * 
 * <p>Each method of the listener is declared to throw GraphParseException.
 * You can throw this exception when the data received, for example in
 * attributes, is not the one expected. This will interrupt the parser
 * (GraphReader) that will in turn throw a GraphParseException.</p>
 * 
 * @see org.miv.graphstream.io.GraphReader
 * @author Antoine Dutot
 * @author Yoann Pign�
 * @since 20040911
 */
@Deprecated
public interface GraphReaderListener
{
// Commands

	/**
	 * A graph attribute has been read.
	 * @param attributes A set of pairs (name,attribute) where the name identifies the attributes or
	 *  null if none.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void graphChanged( Map<String,Object> attributes )
		throws GraphParseException;
	
	/**
	 * A node has been read. The node is identified by a string.
	 * @param id The node unique identifier.
	 * @param attributes A set of pairs (name,attribute) where the name identifies the attributes or
	 *  null if none.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void nodeAdded( String id, Map<String,Object> attributes )
		throws GraphParseException;

	/**
	 * Any of the attributes of the node changed. 
	 * @param id The node unique identifier.
	 * @param attributes A set of pairs (name,attribute) where the name identifies the attributes or
	 *  null if none.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void nodeChanged( String id, Map<String,Object> attributes )
		throws GraphParseException;
	
	/**
	 * A node disappeared.
	 * @param id The node unique identifier.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void nodeRemoved( String id )
		throws GraphParseException;

	/**
	 * An edge has been read.
	 * @param id BufferedEdge identifier.
	 * @param from The source node identifier.
	 * @param to The target node identifier.
	 * @param directed If true the edge is directed from the "from" node to the "to" node.
	 * @param attributes A set of pairs (name,attribute) where the name identifies the attributes or null if none.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void edgeAdded( String id, String from, String to, boolean directed, Map<String,Object> attributes )
		throws GraphParseException;
	
	/**
	 * Any of the attributes changed.
	 * @param id The edge unique identifier.
	 * @param attributes A set of pairs (name,attribute) where the name identifies the attributes or null if none.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void edgeChanged( String id, Map<String,Object> attributes )
		throws GraphParseException;
	
	/**
	 * An edge disappeared.
	 * @param id The edge unique identifier.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void edgeRemoved( String id )
		throws GraphParseException;

	/**
	 * A new step (group of events) begins.
	 * @param time
	 * @throws GraphParseException
	 */
	void stepBegins( double time )
		throws GraphParseException;
	
	/**
	 * Something unknown has been read by the parser and is passed as is.
	 * @param unknown The data read.
	 * @throws GraphParseException If something you expect from the read event did not occurred.
	 */
	void unknownEventDetected( String unknown )
		throws GraphParseException;
}