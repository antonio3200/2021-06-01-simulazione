package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Genes> getVertici(Map<String,Genes> idMap){
		String sql="SELECT DISTINCT  g.GeneID AS id, g.Essential AS es,g.Chromosome AS chro "
				+ "FROM genes g "
				+ "WHERE g.Essential='Essential' "
				+ "ORDER BY g.GeneID";
		List<Genes> result= new LinkedList<Genes>();
		Connection conn= DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Genes genes = new Genes(rs.getString("id"), 
						rs.getString("es"), 
						rs.getInt("chro"));
				result.add(genes);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("SQL ERROR");
		}
		return result;
	}

public List<Arco> getArchi(Map<String,Genes> idMap){
	String sql="SELECT i.GeneID1 AS id1,i.GeneID2 AS id2,i.Expression_Corr AS peso "
			+ "FROM interactions i "
			+ "WHERE i.GeneID1<>i.GeneID2";
	List<Arco> result= new LinkedList<>();
	Connection conn= DBConnect.getConnection();
	try {
		PreparedStatement st= conn.prepareStatement(sql);
		ResultSet rs= st.executeQuery();
		while(rs.next()) {
			Genes g1= idMap.get(rs.getString("id1"));
			Genes g2= idMap.get(rs.getString("id2"));
			double peso= rs.getDouble("peso");
			if(g1!=null && g2!=null) {
			Arco a = new Arco(g1,g2,peso);
			result.add(a);
			}
		}
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new RuntimeException("SQL ERROR");
	}
	return result;
}
	
}
