package ru.innopolis.refrigerator.core.db.jdbc.dao.ingredientcategory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.refrigerator.core.db.dao.ingredientcategory.IngredientCategoryDAO;
import ru.innopolis.refrigerator.core.db.jdbc.connection.postgresql.ConnectionFactoryPostgreSQL;
import ru.innopolis.refrigerator.core.db.jdbc.connection.ConnectionFactory;
import ru.innopolis.refrigerator.core.db.exception.IngredientCategoryDAOException;
import ru.innopolis.refrigerator.core.model.ingredientcategory.IngredientCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class IngredientCategoryDAOJDBCImpl implements IngredientCategoryDAO<IngredientCategory> {

	private static ConnectionFactory connection;
	private static final Logger logger = LogManager.getLogger(IngredientCategoryDAOJDBCImpl.class.getName());

	static {
		connection = ConnectionFactoryPostgreSQL.getInstance();
	}

	@Override
	public List<IngredientCategory> getAll() throws IngredientCategoryDAOException {
		List<IngredientCategory> ingredientCategoryList = new ArrayList<>();
		try {
			Statement statement = connection.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM \"IngredientCategory\"");
			while (resultSet.next()) {
				IngredientCategory ingredientCategory = new IngredientCategory(resultSet.getString("ingredientcategorname"));
				ingredientCategoryList.add(ingredientCategory);
			}
		} catch (SQLException e) {
			logger.error("I can not get of all items to the database" + e.toString());
			throw new IngredientCategoryDAOException();
		}
		return ingredientCategoryList;
	}

	@Override
	public IngredientCategory getById(Long elId) throws IngredientCategoryDAOException {
		String msg = "This method is not implemented";
		logger.error(msg);
		throw new IngredientCategoryDAOException(msg);
	}

	@Override
	public IngredientCategory update(IngredientCategory el) throws IngredientCategoryDAOException{
		String msg = "This method is not implemented";
		logger.error(msg);
		throw new IngredientCategoryDAOException(msg);
	}

	@Override
	public Set<IngredientCategory> getAllById(Set<Long> ids) throws IngredientCategoryDAOException {
		Set<IngredientCategory> ingredientCategoryList = new HashSet<>();
		IngredientCategory ingredientCategory;
		for (Long id: ids
			 ) {
			try {
				Statement statement = connection.getConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM \"IngredientCategory\" where id=" +id);
				if (resultSet.next()) {
					ingredientCategory = new IngredientCategory(resultSet.getString("name"));
					ingredientCategoryList.add(ingredientCategory);
				}
			} catch (SQLException e) {
				logger.error("I can not get an item to the database" + e.toString());
				throw new IngredientCategoryDAOException();
			}
		}

		return ingredientCategoryList;
	}

	@Override
	public IngredientCategory add(IngredientCategory ingredientCategory) throws IngredientCategoryDAOException {
		String sql = "INSERT INTO \"IngredientCategory\" (ingredientCategorName) VALUES(?)";
		try {
			PreparedStatement ps = connection.getConnection().prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, ingredientCategory.getName());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			logger.error("I can not add an item to the database" + e.toString());
			throw new IngredientCategoryDAOException();
		}
		return ingredientCategory;
	}

	@Override
	public IngredientCategory delete(IngredientCategory el) throws IngredientCategoryDAOException {
		String msg = "This method is not implemented";
		logger.error(msg);
		throw new IngredientCategoryDAOException(msg);
	}

	@Override
	public Collection<IngredientCategory> addAll(Collection<IngredientCategory> ingredientCategories) throws IngredientCategoryDAOException {
		String sql = "INSERT INTO \"IngredientCategory\" (ingredientCategorName) VALUES(?)";
		try {
			PreparedStatement ps = connection.getConnection().prepareStatement(sql);
			for (IngredientCategory ingredientCategory : ingredientCategories) {
				ps.clearParameters();
				ps.setString(1, ingredientCategory.getName());
				ps.addBatch();
			}
			ps.executeBatch();
		}
		catch (SQLException e) {
			logger.error("I can not add an item to the database" + e.toString());
			throw new IngredientCategoryDAOException();
		}
		return ingredientCategories;
	}

	@Override
	public Set<Long> getAllId(Set<IngredientCategory> ingredientCategories) throws IngredientCategoryDAOException {
		Set<Long> ingredientCategoryIds = new HashSet<>();
		for (IngredientCategory ingredientCategory:
			 ingredientCategories) {
			long ingredientCategoryId = 0;
			try {
				PreparedStatement statement = connection.getConnection().prepareStatement("SELECT * FROM \"IngredientCategory\" WHERE ingredientCategorName=?");
				statement.setString(1, ingredientCategory.getName());
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					ingredientCategoryId = resultSet.getLong("id");
					if (ingredientCategoryId > 0) {
						ingredientCategoryIds.add(ingredientCategoryId);
					}else {
						logger.error("I can not get an item to the database for " + ingredientCategory.toString());
						throw new IngredientCategoryDAOException();
					}
				}
			}
			catch (SQLException e) {
				logger.error("I can not get an item to the database" + e.toString());
				throw new IngredientCategoryDAOException();
			}
		}

		return ingredientCategoryIds;
	}
}
