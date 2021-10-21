package service;

import dao.DataAccessException;
import dao.Database;
import service.result.ClearResult;

/**
 * Service for /clear.
 */
public class ClearService {
    private final Database db = new Database();

    /**
     * Calls the /clear API.
     * @return the response from the API.
     */
    public ClearResult clear() throws DataAccessException {
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            return new ClearResult(true, "Clear succeeded");
        } catch (DataAccessException e) {
            db.closeConnection(false);
            return new ClearResult(false, "Error: Failed to clear table");
        }
    }
}
