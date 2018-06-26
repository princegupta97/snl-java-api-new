import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.*;
import org.testng.asserts.*;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class TestSnl 
{
	Board board;
	JSONObject data;
	int flag=1;
	@BeforeMethod
	public void TestSnl1() throws FileNotFoundException, UnsupportedEncodingException, IOException
	{
		board = new Board();
	}
	
	@Test(expectedExceptions=PlayerExistsException.class)
	public void playerIsAlreadyRegisterd() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
	{
	    
		board.registerPlayer("PRince");
		board.registerPlayer("PRince");
	}

	@Test(expectedExceptions = MaxPlayersReachedExeption.class,priority=1)
	public void addMaxPlayers() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
	{
		board.registerPlayer("PRhince");
		board.registerPlayer("Prdince");
		board.registerPlayer("PRince1");
		board.registerPlayer("Prince1");
		board.registerPlayer("max playuer");
	}

	@Test
	public void deleteRegisteredPlayer() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException
	{
		board.registerPlayer("PRhince");
		board.registerPlayer("Prdince");
		board.registerPlayer("PRince1");
		JSONObject data = board.getData();
		JSONObject players = data.getJSONArray("players").getJSONObject(0);
		UUID playerID = (UUID)players.get("uuid");
		board.deletePlayer(playerID);
		Assert.assertEquals(2,data.getJSONArray("players").length());
		
	}
	
	@Test(expectedExceptions=NoUserWithSuchUUIDException.class)
	public void DeleteUnregisteredPlayer() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException 
	{
		UUID id = UUID.randomUUID();
		board.deletePlayer(id);
	}
	
	@Test(expectedExceptions=GameInProgressException.class)
	public void GameInProgressException() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, com.qainfotech.tap.training.snl.api.GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException
	{
		
		board.registerPlayer("Prince");
		board.registerPlayer("Prince1");
		JSONObject data = board.getData();
		JSONArray players = data.getJSONArray("players");
		JSONObject players1 = players.getJSONObject(0);
		System.out.println(players);
		//players.put("position",2 );
		UUID playerID = (UUID)players1.get("uuid");
		board.rollDice(playerID);
		board.registerPlayer("PRjd");
	}
	
	@Test(expectedExceptions = InvalidTurnException.class)
	public void test_Invalid_turn() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, com.qainfotech.tap.training.snl.api.GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException
	{
		board.registerPlayer("Prince");
		board.registerPlayer("Gupta");
		board.registerPlayer("Prince1");
		
		JSONObject data = board.getData();
		JSONObject players = data.getJSONArray("players").getJSONObject(1);
		UUID playerID = (UUID)players.get("uuid");
		board.rollDice(playerID);
	}
	
	@Test
	public void test_position_chnaged_or_not() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, com.qainfotech.tap.training.snl.api.GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException
	{
		board.registerPlayer("Prince");
		board.registerPlayer("Gupta");
		board.registerPlayer("Prince1");
		JSONObject data = board.getData();
		JSONObject players = data.getJSONArray("players").getJSONObject(0);
		int position = players.getInt("position");
		UUID playerID = (UUID)players.get("uuid");
		board.rollDice(playerID);
		Assert.assertNotEquals(position, players.getInt("position"));
	}
	
	@Test
	public void checkThatTurnIsChanging()  throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, com.qainfotech.tap.training.snl.api.GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException
	{
		board.registerPlayer("Prince");
		board.registerPlayer("Gupta");
		board.registerPlayer("Prince1");
		JSONObject data = board.getData();
		JSONObject players = data.getJSONArray("players").getJSONObject(0);
		UUID playerID = (UUID)players.get("uuid");
		board.rollDice(playerID);
		int turn = data.getInt("turn");
		Assert.assertEquals(1, turn);

		
	}
	
	

}
