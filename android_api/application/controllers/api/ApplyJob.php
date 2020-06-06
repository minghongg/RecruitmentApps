<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class ApplyJob extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Job_model','job');
		}
		public function index_post(){
			$nim = $this->post('NIM');
			$jobID = $this->post('JobID');
			$job = $this->job->checkJob($nim,$jobID);
			if($job>0){
				$this->response([
					'status' => 200,
					'message' => "Already Applied!",
                ], REST_Controller::HTTP_OK ); 
			}
			else{
				$data = [
					'NIM' => $this->post('NIM'),
					'JobID' => $this->post('JobID'),
					'ApplimentStatus' => 'Pending'
				];
				if($this->job->applyJob($data)>0){
					$this->response([
						'status' => 200,
						'message' => "Successfully applied!",
					], REST_Controller::HTTP_CREATED ); 
				}
			}
		}
	}
?>
