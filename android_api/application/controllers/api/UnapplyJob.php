<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class UnapplyJob extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Job_model','job');
		}
		public function index_post(){
			$nim = $this->post('NIM');
			$jobID = $this->post('JobID');
			$job = $this->job->unapplyJob($nim,$jobID);
			if($job>0){
				$this->response([
					'status' => 200,
					'message' => "Successfully unapplied job!",
                ], REST_Controller::HTTP_OK ); 
			}
		}
	}
?>
