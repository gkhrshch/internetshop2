package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static String DB_NAME = "internetshop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item){
        Statement statement = null;
        String query = "INSERT INTO " + DB_NAME
                + ".items (`name`, `price`) VALUES ( '"
                + item.getName() + "', " + item.getPrice() + ");";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            logger.warn(e);
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Item add failed, no ID obtained.");
            }
        } catch (SQLException e) {
            logger.warn(e);
        }
        return item;
    }

    @Override
    public Item get(Long id) {
        Statement statement = null;
        String query = "SELECT * FROM `" + DB_NAME + "`.`items` WHERE item_id=" + id + ";";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        Item toReturn = get(item.getId());
        Statement statement = null;
        String query = "UPDATE `" + DB_NAME
                + "`.`items` + SET name='" + item.getName()
                + "' WHERE item_id=" + item.getId();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Item update failed", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return toReturn;
    }

    @Override
    public Item delete(Long id) {
        Item item = get(id);
        Statement statement = null;
        String query = "DELETE FROM `" + DB_NAME
                + "`.`items` WHERE item_id=" + id;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Item deletion failed", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return item;
    }

    @Override
    public List<Item> getAll(){
        List<Item> items = new ArrayList<>();
        Statement statement = null;
        String query = "SELECT * FROM `" + DB_NAME + "`.`items`;";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            logger.warn("Can't get items list", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return items;
    }
}
