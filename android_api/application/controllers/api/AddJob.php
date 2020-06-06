<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class AddJob extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Job_model','job');
		}
		public function index_post(){
			$jobTitle = $this->post('JobName');
			$jobDesc = $this->post('JobDesc');
			$jobQuota = $this->post('JobQuota');
			$perusahaanID = $this->post('PerusahaanID');
			$job = $this->job->addJob($jobTitle,$jobDesc,$jobQuota,$perusahaanID);
			if($job>0){
				$this->response([
					'status' => 200,
					'message' => "Insert Successful!",
                ], REST_Controller::HTTP_OK ); 
			}
			else{
				$this->response([
					'status' => 200,
					'message' => "Insert Failed!",
                ], REST_Controller::HTTP_OK ); 
			}
		}
	}
?>
