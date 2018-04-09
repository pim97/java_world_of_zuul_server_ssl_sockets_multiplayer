package game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javax.net.ssl.SSLServerSocketFactory;

import game.commands.CommandAction;
import game.configs.Global;
import game.game.Game;
import game.game.entity.Player;
import game.sound.Sound;

/**
* @author Pim de Bree
* @since 17-11-2017
* @version 1.0
*/
public class Server {

	/**
	 * The port the server is currently using
	 */
	public static final int PORT = Global.GAME_PORT;
	
	public Server(Game game) {
		setGame(game);
	}
	
	/**
	 * All the current connections the server currently has
	 */
	private ArrayList<Handler> connections = new ArrayList<Handler>();
	
	/**
	 * Sends data to the client
	 * @param data
	 */
	public void sendStringToClients(String data) {
		getConnections().stream().filter(Objects::nonNull).forEach(connection -> {
			connection.getOut().println(data);
			System.out.println("Sent the following data to all the clients: "+data);
		});
	}
	
	/**
	 * Initializing the server
	 * @throws IOException -> could not initalize server
	 */
	public void initializeServer() throws IOException {
		System.out.println("Initializing server");
		Thread t1 = new Thread() {
			public void run() {
			System.setProperty("javax.net.ssl.keyStore", Global.KEYSTORE_FILE_NAME);
			System.setProperty("javax.net.ssl.keyStorePassword", Global.KEYSTORE_PASSWORD);
			SSLServerSocketFactory ssf = 
	                (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			
				try {
					ServerSocket listener = 
		                    ssf.createServerSocket(PORT);
					//listener = ssf.createServerSocket(PORT);//new ServerSocket(PORT);
					while (true) {
						System.out.println("New connection "+listener);
		                Handler conn = new Handler(getGame(), listener.accept());
		                getConnections().add(conn);
		                conn.start();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.start();
		
	}
	
	
	private Game game;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public ArrayList<Handler> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Handler> connections) {
		this.connections = connections;
	}
	
	public Optional<Handler> getHandler(int id) {
		return getGame().getServer().getConnections().stream().filter(con -> con.getPlayerId() == id).findFirst();
	}

	/**
	 * Elke connectie krijgt een nieuwe klas met een thread
	 * @author pim
	 */
	private static class Handler extends Thread {
		private Player player;
		private int playerId = -1;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		private Game game;

		public Handler(Game game, Socket socket) {
            this.socket = socket;
            this.game = game;
        }
		
		public void run() {
			try {
				setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
				setOut(new PrintWriter(socket.getOutputStream(), true));
				//Sound.sendSoundToClient("background");
				Sound.sendRepeatSoundToClient("background");
				Sound.sendRepeatSoundToClient("dungeon");
			
				while (true) {
					String text = in.readLine();
					String[] splitted = text.split(" ");
					
					if (text.contains("::USERNAMEREQUESTSEND")) {
						if (splitted.length > 1) {
							if (getGame().getPlayer() == null) {
								getGame().setPlayer(new Player(getGame(), "Game of Thrones Game"));
							}
							if (getPlayerId() == -1) {
								setPlayerId(Integer.parseInt(splitted[1]));
								setPlayer(new Player(getGame(), splitted[1]));
								System.out.println("New connection id: "+splitted[1]);
								getGame().printText("::PLAYERCOMMANDCONSOLE A new player connected: "+getPlayerId());
								
							}
						}
					} else if (text.contains("::SETNAME")) {
						String[] splittedText = text.split(" ");
						
						if (splittedText.length > 1) {
							int id = Integer.parseInt(splittedText[1]);
							String newName = splittedText[2];
							
							getGame().getServer().getConnections().stream().filter(con -> con.getPlayerId() == id).forEach(player -> {
								getGame().printText("::PLAYERCOMMANDCONSOLE "+getPlayerId()+" has changed his name to: "+newName);
								if (player.getPlayer() == null) {
									player.setPlayer(new Player(getGame(), newName));
								} else {
									player.getPlayer().setName(newName);
								}
							});
						}
					} else {
						CommandAction command = getGame().getParser().getCommand(text);
						
						if (command != null) {
							getGame().getCommmandHandler().processCommand(command);
							
							getGame().printText("::PLAYERCOMMANDCONSOLE Playername: "+getPlayer().getName()+" Playerid: "+getPlayerId()+" - "+text);
						} else {
							getGame().printText("No command found, please use 'help'");
							Sound.sendSoundToClient("typing");
						}
						System.out.println("Data received from client: "+text);
					}
				}
				
			}catch (IOException e) {
				//e.getCause();
				//e.printStackTrace();
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                try {
                	Optional<Handler> handler = getGame().getServer().getHandler(getPlayerId());
                	if (handler.isPresent()) {
                		System.out.println("Connection was removed from the list");
                		getGame().getServer().getConnections().remove(handler.get());
                	}
                    socket.close();
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }
		}
		
		public Game getGame() {
			return game;
		}

		public PrintWriter getOut() {
			return out;
		}

		public void setOut(PrintWriter out) {
			this.out = out;
		}
		public void setIn(BufferedReader in) {
			this.in = in;
		}

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}
	}
}
