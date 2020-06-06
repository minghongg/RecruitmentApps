<?php


class Job_model extends CI_Model{

	public function checkJob($nim,$jobID){
		$this->db->select('*');
		$this->db->from('trappliment');
		$this->db->where('NIM',$nim);
		$this->db->where('JobID',$jobID);
		$query = $this->db->get();
		$result = $query->row_array();
		return $result;
	}

	public function checkJobAppliment($nim){
		$this->db->select('*');
		$this->db->from('trappliment');
		$this->db->join('trjob','trjob.JobID=trappliment.JobID');
		$this->db->join('msperusahaan','trjob.PerusahaanID=msperusahaan.PerusahaanID');
		$this->db->where('NIM',$nim);
		$query = $this->db->get();
		$result = $query->result_array();
		return $result;
	}

	public function applyJob($data){
		$this->db->insert('trappliment',$data);
		return $this->db->affected_rows();
	}

	public function unapplyJob($nim,$jobID){
		$this->db->where('JobID',$jobID);
		$this->db->where('NIM',$nim);
		$this->db->delete('trappliment');
		return $this->db->affected_rows();
	}

	public function jobDetail($id){
		$this->db->select('*');
		$this->db->from('trjob');
		$this->db->where('JobID',$id);
		$query = $this->db->get();
		$result = $query->result_array();
		return $result;
	}

	public function checkQuota($jobID){
		$this->db->select('JobQuota');
		$this->db->from('trjob');
		$this->db->where('JobID',$jobID);
		$this->db->where('JobQuota >','0');
		$query = $this->db->get();
		$result = $query->row_array();
		return $result;
	}

	public function updateAppliment($id,$status){
		$data = [
			'ApplimentStatus' => $status
		];
		$this->db->where('ApplimentID',$id);
		$this->db->update('trappliment',$data);
		return $this->db->affected_rows();
	}
	public function updateQuota($id){
		$this->db->where('JobID',$id);
		$this->db->set('JobQuota','`JobQuota`-1',FALSE);
		$this->db->update('trjob');
		return $this->db->affected_rows();
	}
	public function addJob($jobName,$jobDesc,$jobQuota,$perusahaanID){
		$data = array(
			'JobName' => $jobName,
			'JobDesc' => $jobDesc,
			'JobQuota' => $jobQuota,
			'PerusahaanID' => $perusahaanID
		);
		$this->db->insert('trjob',$data);
		return $this->db->affected_rows();
	}

}

?>
