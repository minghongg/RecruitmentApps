<?php


class Perusahaan_model extends CI_Model{

	public function listPerusahaan(){
		return $this->db->get('msperusahaan')->result_array();
	}
	public function detailPerusahaan($id){
		$this->db->select('*');
		$this->db->from('trjob');
		$this->db->where('PerusahaanID',$id);
		$query = $this->db->get();
		$result = $query->result_array();
		return $result;
	}
	public function phonePerusahaan($id){
		$this->db->select('PhoneNumber');
		$this->db->from('msperusahaanphone');
		$this->db->where('PerusahaanID',$id);
		$query = $this->db->get();
		$result = $query->result();
		return $result;
	}
	public function getPerusahaanLogin($email,$password){
		$this->db->select('*');
		$this->db->from('msperusahaan');
		$this->db->where('Email',$email);
		$this->db->where('Password',$password);
		$query = $this->db->get();
		$result = $query->row_array();
		return $result;
	}
	public function getApplicants($perusahaanID){
		$this->db->select('*');
		$this->db->from('trjob');
		$this->db->join('trappliment','trjob.JobID=trappliment.JobID');
		$this->db->join('msuser','trappliment.NIM=msuser.NIM');
		$this->db->where('PerusahaanID',$perusahaanID);
		$query = $this->db->get();
		$result = $query->result_array();
		return $result;
	}

}

?>
