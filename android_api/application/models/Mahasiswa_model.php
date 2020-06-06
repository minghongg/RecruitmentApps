<?php


class Mahasiswa_model extends CI_Model{

	public function getUserLogin($email,$password){
		$this->db->select('*');
		$this->db->from('msuser');
		$this->db->where('Email',$email);
		$this->db->where('Password',$password);
		$query = $this->db->get();
		$result = $query->row_array();
		return $result;
	}
	public function getProfile($nim){
		$this->db->select("Nama,NIM,Gender,Jurusan,DOB,PlaceofBirth,IPK,CurriculumVitae,Photo,Role");
		$this->db->from('msuser');
		$this->db->where('NIM',$nim);
		$query = $this->db->get();
		$result = $query->row_array();
		return $result;
	}

}

?>
